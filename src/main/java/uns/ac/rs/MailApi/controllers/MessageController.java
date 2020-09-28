package uns.ac.rs.MailApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uns.ac.rs.MailApi.common.MoveMessageRequest;
import uns.ac.rs.MailApi.dto.AttachmentDTO;
import uns.ac.rs.MailApi.dto.MessageDTO;
import uns.ac.rs.MailApi.dto.TagDTO;
import uns.ac.rs.MailApi.entity.*;
import uns.ac.rs.MailApi.lucene.Indexer;
import uns.ac.rs.MailApi.lucene.model.IndexUnit;
import uns.ac.rs.MailApi.mail.MailAPI;
import uns.ac.rs.MailApi.service.AccountService;
import uns.ac.rs.MailApi.service.FolderService;
import uns.ac.rs.MailApi.service.MessageService;
import uns.ac.rs.MailApi.service.UserService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("acc/{index}/messages")
public class MessageController {

    private static String DATA_DIR_PATH;

    static {
        ResourceBundle rb=ResourceBundle.getBundle("application");
        DATA_DIR_PATH=rb.getString("dataDir");
    }

    @Autowired
    private MailAPI mailAPI;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FolderService folderService;

    /**
     * Loads all messages from inbox folder in the database for an Account. Make
     * sure that syncAccount() method is called before so that you can get updated
     * e-mails list.
     *
     * @param Index that represents user's account in the URL.
     */
    @GetMapping
    public ResponseEntity<?> getAllMessages(
            @PathVariable("index") int accountIndex,
            Principal principal) {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Preparing data for front end -----
        List<MessageDTO> messagesDTO = new ArrayList<MessageDTO>();
        for (Message message : messageService.getMessages(account)) {
            messagesDTO.add(new MessageDTO(message));
        }

        return new ResponseEntity<List<MessageDTO>>(messagesDTO, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchedMessages(@PathVariable("index") int accountIndex, Principal principal,
                                                 @RequestParam Optional<String> userEmail) {

        if (accountIndex < 0) {
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        }

        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null) {
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);
        }

        List<MessageDTO> messagesDTO = new ArrayList<MessageDTO>();

        if (userEmail != null) {
            List<Message> mess = messageService.findByFrom(userEmail.orElse("_"));
            for (Message message : mess) {

                messagesDTO.add(new MessageDTO(message));
                return new ResponseEntity<List<MessageDTO>>(messagesDTO, HttpStatus.OK);
            }
        }
        return new ResponseEntity<List<MessageDTO>>(messagesDTO, HttpStatus.OK);

    }

    /**
     * @deprecated This method was used to get new messages from JavaMailAPI. Now
     *             that is handled when a user logs in, by calling a method
     *             syncAccount().
     *
     */
    @Deprecated
    @GetMapping("/new")
    public ResponseEntity<?> getAllNewMessages(@PathVariable("index") int accountIndex, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        List<Account> userAccounts = user.getAccounts();

        Account userAccount;

        try {
            userAccount = userAccounts.get(accountIndex);
        } catch (IndexOutOfBoundsException ex) {
            return new ResponseEntity<>("Unexisting account!", HttpStatus.BAD_REQUEST);
        }
        List<MessageDTO> messages = new ArrayList<MessageDTO>();

        for (Message message : messageService.findAllUnread(userAccount)) {
            if (!message.getFolder().getName().equals("Drafts") && !message.getFolder().getName().equals("Outbox"))
                messages.add(new MessageDTO(message));
        }

        return new ResponseEntity<List<MessageDTO>>(messages, HttpStatus.OK);
    }

    /**
     * Synchronizes missing mails in database that have arrived to the account using
     * JavaMailAPI.
     *
     * @param Account for which synchronization is done.
     */
    @PostMapping("/sync")
    public ResponseEntity<?> syncAccount(@PathVariable("index") int accountIndex, Principal principal) {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Applying rules to new e-mails -----
        List<Message> messages = mailAPI.loadMessages(account);
        System.out.println("API messages: " + messages.size());
        messages = applyRules(messages, account);

        // ----- Synchronizing database e-mails -----
        for (Message message : messages) {
            messageService.save(message);
        }

        // ----- Updating last synchronization time for the account -----
        account.setLastSyncTime(new Timestamp(System.currentTimeMillis()));
        accountService.save(account);

        return new ResponseEntity<String>("Synchronization complete!", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessageById(@PathVariable("index") int accountIndex,
                                            @PathVariable("id") Integer messageId, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        List<Account> userAccounts = user.getAccounts();

        Account userAccount;

        try {
            userAccount = userAccounts.get(accountIndex);
        } catch (IndexOutOfBoundsException ex) {
            return new ResponseEntity<>("Unexisting account!", HttpStatus.BAD_REQUEST);
        }

        Message message = messageService.findOne(messageId, userAccount);

        MessageDTO messageDTO;
        if (message != null) {
            messageDTO = new MessageDTO(message);
            return new ResponseEntity<MessageDTO>(messageDTO, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> saveMessage(
            @PathVariable("index") int accountIndex,
            @RequestBody MessageDTO messageDTO,
            Principal principal) {

        //IndexUnit klasa predstavlja model podataka koji ce se indeksirati i koji ce biti potrebni za pretragu
        IndexUnit indexUnit = new IndexUnit();
        indexUnit.setTitle(messageDTO.getSubject());
        indexUnit.setText(messageDTO.getContent());
        indexUnit.setReceiver(messageDTO.getTo().get(0).getEmail());
        indexUnit.setSender(messageDTO.getContent());
        indexUnit.setPdf(messageDTO.getSubject());
        //getInstance predstavlja singleton
        Indexer.getInstance().add(indexUnit.getLuceneDocument());
        System.out.println(indexUnit.toString());

        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.BAD_REQUEST);

        // ----- Obtaining data from database -----
        Folder folder = folderService.findOutbox(account);
        if (folder == null)
            return new ResponseEntity<>("Unexisting outbox folder.", HttpStatus.BAD_REQUEST);

        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            return new ResponseEntity<>("Unexisting user.", HttpStatus.BAD_REQUEST);
        }

        // ----- Setting data from front end -----
        Message message = new Message();
        message.setSubject(messageDTO.getSubject());
        message.setContent(messageDTO.getContent());
        message.setTo(MessageDTO.contactsToString(messageDTO.getTo()));
        message.setCc(MessageDTO.contactsToString(messageDTO.getCc()));
        message.setBcc(MessageDTO.contactsToString(messageDTO.getBcc()));
        ArrayList<Attachment> attachments = new ArrayList<Attachment>();

        if (messageDTO.getAttachments().size() > 0) {
            for (AttachmentDTO attachmentDTO : messageDTO.getAttachments()) {
                Attachment attachment = new Attachment();

                attachment.setName(attachmentDTO.getName());
                attachment.setMimeType(attachmentDTO.getMimeType());
                attachment.setData(attachmentDTO.getData());
                attachment.setMessage(message);

                attachments.add(attachment);
            }

            message.setAttachments(attachments);
        } else {
            message.setAttachments(new ArrayList<Attachment>());
        }

        ArrayList<Tag> tags = new ArrayList<>();
        if (messageDTO.getTags().size() >= 1) {
            for (TagDTO tagDTO : messageDTO.getTags()) {

                Tag tag = new Tag();
                tag.setName(tagDTO.getName());
                tag.setUser(user);

                tags.add(tag);
            }
        }

        message.setTags(tags);
        // ----- Setting data from server -----
        message.setFrom(account.getUsername() + "@gmail.com");
        message.setDateTime(new Timestamp(System.currentTimeMillis()));
        message.setAccount(account);
        message.setFolder(folder);
        message.setUnread(true);

        // ----- Attempt to send an e-mail -----
        if (mailAPI.sendMessage(message)) {
            // ----- Saving to database -----

            message = messageService.save(message);
            return new ResponseEntity<MessageDTO>(new MessageDTO(message), HttpStatus.OK);
        } else
            return new ResponseEntity<MessageDTO>(new MessageDTO(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private File getResourceFilePath(String path) {
        URL url = this.getClass().getClassLoader().getResource(path);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        return file;
    }

    @PostMapping("/draft")
    public ResponseEntity<?> saveDraft(@PathVariable("index") int accountIndex, @RequestBody MessageDTO messageDTO,
                                       Principal principal) {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.BAD_REQUEST);

        // ----- Obtaining data from database -----
        Folder folder = folderService.findDraft(account);
        if (folder == null)
            return new ResponseEntity<>("Unexisting draft folder.", HttpStatus.BAD_REQUEST);

        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            return new ResponseEntity<>("Unexisting user.", HttpStatus.BAD_REQUEST);
        }
        // ----- Setting data from front end -----
        Message message = new Message();
        message.setSubject(messageDTO.getSubject());
        message.setContent(messageDTO.getContent());
        message.setFrom(account.getUsername() + "@gmail.com");
        message.setTo(MessageDTO.contactsToString(messageDTO.getTo()));
        message.setCc(MessageDTO.contactsToString(messageDTO.getCc()));
        message.setBcc(MessageDTO.contactsToString(messageDTO.getBcc()));

        ArrayList<Attachment> attachments = new ArrayList<Attachment>();

        if (messageDTO.getAttachments().size() > 0) {
            for (AttachmentDTO attachmentDTO : messageDTO.getAttachments()) {
                Attachment attachment = new Attachment();

                attachment.setName(attachmentDTO.getName());
                attachment.setMimeType(attachmentDTO.getMimeType());
                attachment.setData(attachmentDTO.getData());
                attachment.setMessage(message);

                attachments.add(attachment);
            }

            message.setAttachments(attachments);
        } else {
            message.setAttachments(new ArrayList<Attachment>());
        }

        ArrayList<Tag> tags = new ArrayList<>();
        if (messageDTO.getTags().size() >= 1) {
            for (TagDTO tagDTO : messageDTO.getTags()) {

                Tag tag = new Tag();
                tag.setName(tagDTO.getName());
                tag.setUser(user);

                tags.add(tag);

            }
            message.setTags(tags);
        }

        // ----- Setting data from server -----
        message.setDateTime(new Timestamp(System.currentTimeMillis()));
        message.setAccount(account);

        message.setFolder(folder);
        message.setUnread(true);

        // ----- Saving to database -----
        message = messageService.save(message);

        return new ResponseEntity<MessageDTO>(new MessageDTO(message), HttpStatus.OK);
    }

    @PostMapping("/move")
    public ResponseEntity<?> moveMessageToFolder(
            @PathVariable("index") int accountIndex,
            @RequestBody MoveMessageRequest moveMessageRequest,
            Principal principal) {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        else if (moveMessageRequest.getMessageId() < 1)
            return new ResponseEntity<>("You must provide an ID for a message you want to move!",
                    HttpStatus.BAD_REQUEST);
        else if (moveMessageRequest.getFolderId() < 1)
            return new ResponseEntity<>("You must provide an ID for a folder you want to move your message to!",
                    HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Obtaining data from database -----
        Folder folder = folderService.findOne(moveMessageRequest.getFolderId(), account);
        if (folder == null)
            return new ResponseEntity<>("Unexisting folder.", HttpStatus.NOT_FOUND);

        Message message = messageService.findOne(moveMessageRequest.getMessageId(), account);
        if (message == null)
            return new ResponseEntity<>("Unexisting message.", HttpStatus.NOT_FOUND);

        // ----- Setting new data -----
        message.setFolder(folder);

        // ----- Saving to database -----
        message = messageService.save(message);

        return new ResponseEntity<String>("Successfully moved.", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMessage(@PathVariable("index") int accountIndex,
                                           @PathVariable("id") Integer messageId, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        List<Account> userAccounts = user.getAccounts();

        Account userAccount;

        try {
            userAccount = userAccounts.get(accountIndex);
        } catch (IndexOutOfBoundsException ex) {
            return new ResponseEntity<>("Unexisting account.", HttpStatus.BAD_REQUEST);
        }

        Message message = messageService.findOne(messageId, userAccount);

        if (message != null) {
            message.setUnread(false);
            // ----- Saving to database -----
            message = messageService.save(message);

            return new ResponseEntity<MessageDTO>(new MessageDTO(message), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable("index") int accountIndex,
                                           @PathVariable("id") Integer messageId, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        List<Account> userAccounts = user.getAccounts();

        Account userAccount;

        try {
            userAccount = userAccounts.get(accountIndex);
        } catch (IndexOutOfBoundsException ex) {
            return new ResponseEntity<>("Unexisting account!", HttpStatus.BAD_REQUEST);
        }

        Message message = messageService.findOne(messageId, userAccount);

        if (message != null) {
            messageService.remove(messageId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/sorting")
    public ResponseEntity<?> getSortedMessages(@PathVariable("index") int accountIndex, Principal principal,
                                               @RequestParam String sortBy, @RequestParam String asc) {

        if (accountIndex < 0) {
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        }

        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null) {
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);
        }

        List<MessageDTO> messagesDTO = new ArrayList<MessageDTO>();
        Comparator<Message> comparator;

        if (sortBy.equals("subject") && asc.equals("asc")) {
            comparator = new Comparator<Message>() {

                @Override
                public int compare(Message o1, Message o2) {
                    return o1.getSubject().compareTo(o2.getSubject());
                }

            };
        } else if (sortBy.equals("subject") && asc.equals("desc")) {
            comparator = new Comparator<Message>() {

                @Override
                public int compare(Message o1, Message o2) {
                    return o1.getSubject().compareTo(o2.getSubject()) * -1;
                }
            };
        }

        else if (sortBy.equals("from") && asc.equals("asc")) {
            comparator = new Comparator<Message>() {

                @Override
                public int compare(Message o1, Message o2) {
                    return o1.getFrom().compareTo(o2.getFrom());
                }

            };
        } else if (sortBy.equals("from") && asc.equals("desc")) {
            comparator = new Comparator<Message>() {

                @Override
                public int compare(Message o1, Message o2) {
                    return o1.getFrom().compareTo(o2.getFrom()) * -1;
                }
            };

        }else if (sortBy.equals("date") && asc.equals("asc")) {
            comparator = new Comparator<Message>() {

                @Override
                public int compare(Message o1, Message o2) {
                    return o1.getDateTime().compareTo(o2.getDateTime());
                }

            };
        } else if (sortBy.equals("date") && asc.equals("desc")) {
            comparator = new Comparator<Message>() {

                @Override
                public int compare(Message o1, Message o2) {
                    return o1.getDateTime().compareTo(o2.getDateTime()) * -1;
                }
            };

        } else {
            comparator = new Comparator<Message>() {
                @Override
                public int compare(Message o1, Message o2) {
                    return o1.getId().compareTo(o2.getId());
                }

            };
        }

        List<Message> mess = messageService.getMessages(account);
        Collections.sort(mess, comparator);
        for (Message message : mess) {
            messagesDTO.add(new MessageDTO(message));

        }

        return new ResponseEntity<List<MessageDTO>>(messagesDTO, HttpStatus.OK);

    }
    // ----- HELPER METHODS -----

    private List<Message> applyRules(List<Message> messages, Account account) {

        // ----- Obtaining data from database -----
        List<Folder> folders = folderService.findAll(account);
        if (folders == null)
            return messages;

        List<Rule> rules = new ArrayList<Rule>();
        for (Folder folder : folders)
            for (Rule rule : folder.getRules())
                rules.add(rule);

        // ----- Applying rules -----
        List<Message> updatedMessages = new ArrayList<Message>();
        for (Message message : messages)
            updatedMessages.add(message);

        for (Message message : messages) {
            for (Rule rule : rules) {
                switch (rule.getCondition()) {
                    case CC:
                        if (message.getCc().contains(rule.getConditionValue())) {
                            switch (rule.getOperation()) {
                                case COPY:
                                    Message copy = new Message(message);

                                    copy.setFolder(rule.getFolder());

                                    updatedMessages.add(copy);
                                    break;
                                case MOVE:
                                    message.setFolder(rule.getFolder());
                                    if (!updatedMessages.contains(message))
                                        updatedMessages.add(message);
                                    break;
                                case DELETE:
                                    if (rule.getFolder().getName().equals("Inbox"))
                                        updatedMessages.remove(message);
                                    break;
                            }
                        }
                        break;
                    case TO:
                        if (message.getTo().contains(rule.getConditionValue())) {
                            switch (rule.getOperation()) {
                                case COPY:
                                    Message copy = new Message(message);

                                    copy.setFolder(rule.getFolder());

                                    updatedMessages.add(copy);
                                    break;
                                case MOVE:
                                    message.setFolder(rule.getFolder());
                                    if (!updatedMessages.contains(message))
                                        updatedMessages.add(message);
                                    break;
                                case DELETE:
                                    if (rule.getFolder().getName().equals("Inbox"))
                                        updatedMessages.remove(message);
                                    break;
                            }
                        }
                        break;
                    case FROM:
                        if (message.getFrom().contains(rule.getConditionValue())) {
                            switch (rule.getOperation()) {
                                case COPY:
                                    Message copy = new Message(message);

                                    copy.setFolder(rule.getFolder());

                                    updatedMessages.add(copy);
                                    break;
                                case MOVE:
                                    message.setFolder(rule.getFolder());
                                    if (!updatedMessages.contains(message))
                                        updatedMessages.add(message);
                                    break;
                                case DELETE:
                                    if (rule.getFolder().getName().equals("Inbox"))
                                        updatedMessages.remove(message);
                                    break;
                            }
                        }
                        break;
                    case SUBJECT:
                        if (message.getSubject().contains(rule.getConditionValue())) {
                            switch (rule.getOperation()) {
                                case COPY:
                                    Message copy = new Message(message);

                                    copy.setFolder(rule.getFolder());

                                    updatedMessages.add(copy);
                                    break;
                                case MOVE:
                                    message.setFolder(rule.getFolder());
                                    if (!updatedMessages.contains(message))
                                        updatedMessages.add(message);
                                    break;
                                case DELETE:
                                    if (rule.getFolder().getName().equals("Inbox"))
                                        updatedMessages.remove(message);
                                    break;
                            }
                        }
                        break;
                }
            }
        }

        return updatedMessages;
    }


}

