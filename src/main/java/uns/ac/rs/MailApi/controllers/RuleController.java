package uns.ac.rs.MailApi.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.MailApi.common.CopyRuleHandler;
import uns.ac.rs.MailApi.common.DeleteRuleHandler;
import uns.ac.rs.MailApi.common.MoveRuleHandler;
import uns.ac.rs.MailApi.common.RuleHandler;
import uns.ac.rs.MailApi.dto.RuleDTO;
import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Message;
import uns.ac.rs.MailApi.entity.Rule;
import uns.ac.rs.MailApi.service.AccountService;
import uns.ac.rs.MailApi.service.FolderService;
import uns.ac.rs.MailApi.service.MessageService;
import uns.ac.rs.MailApi.service.RuleService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("acc/{index}/folders/{id}/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<?> getRules(
            @PathVariable("index") int accountIndex,
            @PathVariable("id") int folderId,
            Principal principal){

        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        else if (folderId < 1)
            return new ResponseEntity<>("You must provide an ID for a folder!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Resolving current user's folder -----
        Folder folder = folderService.findOne(folderId, account);
        if (folder == null)
            return new ResponseEntity<>("Unexisting folder.", HttpStatus.NOT_FOUND);

        List<RuleDTO> rules =  new ArrayList<RuleDTO>();
        for (Rule rule : ruleService.findByFolder(folder)) {
            rules.add(new RuleDTO(rule));
        }

        return new ResponseEntity<List<RuleDTO>>(rules, HttpStatus.OK);

    }

    @GetMapping("/{ruleId}")
    public ResponseEntity<?> getRule(
            @PathVariable("index") int accountIndex,
            @PathVariable("id") int folderId,
            @PathVariable("ruleId") int ruleId,
            Principal principal){

        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        else if (folderId < 1)
            return new ResponseEntity<>("You must provide an ID for a folder!", HttpStatus.BAD_REQUEST);
        else if (ruleId < 1)
            return new ResponseEntity<>("You must provide an ID for a rule!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Resolving current user's folder -----
        Folder folder = folderService.findOne(folderId, account);
        if (folder == null)
            return new ResponseEntity<>("Unexisting folder.", HttpStatus.NOT_FOUND);

        Rule rule = ruleService.findOne(ruleId);
        if (rule == null)
            return new ResponseEntity<>("Unexisting rule.", HttpStatus.NOT_FOUND);
        else if (!(rule.getFolder().getId() == folder.getId()))
            return new ResponseEntity<>("Unexisting rule in that folder.", HttpStatus.NOT_FOUND);


        return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> createRule(
            @PathVariable("index") int accountIndex,
            @PathVariable("id") int folderId,
            @RequestBody RuleDTO ruleDTO,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        else if (folderId < 1)
            return new ResponseEntity<>("You must provide an ID for a folder!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Resolving current user's folder -----
        Folder folder = folderService.findOne(folderId, account);
        if (folder == null)
            return new ResponseEntity<>("Unexisting folder.", HttpStatus.NOT_FOUND);

        // ----- Setting data from frontend -----
        Rule rule = new Rule();
        rule.setCondition(ruleDTO.getCondition());
        rule.setValue(ruleDTO.getValue());
        rule.setOperation(ruleDTO.getOperation());
        rule.setFolder(folder);

        // ----- Saving to database -----
        ruleService.save(rule);

        return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateRule(
            @PathVariable("index") int accountIndex,
            @PathVariable("id") int folderId,
            @RequestBody RuleDTO ruleDTO,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        else if (folderId < 1)
            return new ResponseEntity<>("You must provide an ID for a folder!", HttpStatus.BAD_REQUEST);
        else if (ruleDTO.getId() == null || ruleDTO.getId() == 0)
            return new ResponseEntity<>("You must provide an ID for a rule!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Resolving current user's folder -----
        Folder folder = folderService.findOne(folderId, account);
        if (folder == null)
            return new ResponseEntity<>("Unexisting folder.", HttpStatus.NOT_FOUND);

        // ----- Obtaining data from database -----
        Rule rule = ruleService.findByIdAndFolder(ruleDTO.getId(), folder);
        if (rule == null)
            return new ResponseEntity<>("Unexisting rule.", HttpStatus.NOT_FOUND);

        // ----- Setting data from frontend -----
        rule.setCondition(ruleDTO.getCondition());
        rule.setValue(ruleDTO.getValue());
        rule.setOperation(ruleDTO.getOperation());

        // ----- Saving to database -----
        ruleService.save(rule);

        return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRule(
            @PathVariable("index") int accountIndex,
            @PathVariable("id") int folderId,
            @RequestBody RuleDTO ruleDTO,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        else if (folderId < 1)
            return new ResponseEntity<>("You must provide an ID for a folder!", HttpStatus.BAD_REQUEST);
        else if (ruleDTO.getId() == 0)
            return new ResponseEntity<>("You must provide an ID for a rule!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Resolving current user's folder -----
        Folder folder = folderService.findOne(folderId, account);
        if (folder == null)
            return new ResponseEntity<>("Unexisting folder.", HttpStatus.NOT_FOUND);

        // ----- Obtaining data from database -----
        Rule rule = ruleService.findByIdAndFolder(ruleDTO.getId(), folder);
        if (rule == null)
            return new ResponseEntity<>("Unexisting rule.", HttpStatus.NOT_FOUND);

        // ----- Removing from database -----
        ruleService.remove(rule.getId());

        return new ResponseEntity<String>("Successfully removed!", HttpStatus.OK);
    }

    /**
     * Applies rules of a selected folder on messages that are currently in
     * inbox.
     *
     * @throws IllegalArgumentException
     */
    @PostMapping("/apply")
    public ResponseEntity<?> applyRules(
            @PathVariable("index") int accountIndex,
            @PathVariable("id") int folderId,
            Principal principal) throws IllegalArgumentException
    {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        else if (folderId < 1)
            return new ResponseEntity<>("You must provide an ID for a folder!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Resolving current user's folder -----
        Folder folder = folderService.findOne(folderId, account);
        if (folder == null)
            return new ResponseEntity<>("Unexisting folder.", HttpStatus.NOT_FOUND);

        // ----- Obtaining data from database -----
        List<Rule> rules = ruleService.findByFolder(folder);
        if (rules == null)
            return new ResponseEntity<>("That folder has no rules.", HttpStatus.NOT_FOUND);

        List<Message> inboxMessages = messageService.getMessages(account);
        if (inboxMessages == null)
            return new ResponseEntity<>("There are no messages to apply rules on.", HttpStatus.NOT_FOUND);

        // ----- Control variable for later message updates -----
        List<Message> folderMessagesBeforeUpdate = new ArrayList<Message>();
        for (Message message : folder.getMessages())
            folderMessagesBeforeUpdate.add(message);

        // ----- Applying rules -----
        for (Rule rule : rules) {
            // ----- Messages that are going to be checked -----
            List<Message> messages;

            // ----- Setting a rule handler that will apply correct logic for rules -----
            RuleHandler ruleHandler;
            switch (rule.getOperation()) {
                case MOVE:
                    ruleHandler = new MoveRuleHandler();
                    messages = inboxMessages;
                    break;
                case COPY:
                    ruleHandler = new CopyRuleHandler();
                    messages = inboxMessages;
                    break;
                case DELETE:
                    ruleHandler = new DeleteRuleHandler();
                    messages = folder.getMessages();
                    break;
                default:
                    throw new IllegalArgumentException("Rule does not have a correct operation!");
            }

            // ----- Extracting value for the rule -----
            String value = rule.getValue();

            // ----- Running a check for matching conditions -----
            switch (rule.getCondition()) {
                case CC:
                    for (int i = 0; i < messages.size(); i++)
                        if (messages.get(i).getCc().contains(value))
                            ruleHandler.applyRule(messages, i, folder);
                    break;
                case TO:
                    for (int i = 0; i < messages.size(); i++)
                        if (messages.get(i).getTo().contains(value))
                            ruleHandler.applyRule(messages, i, folder);
                    break;
                case FROM:
                    for (int i = 0; i < messages.size(); i++)
                        if (messages.get(i).getFrom().contains(value))
                            ruleHandler.applyRule(messages, i, folder);
                    break;
                case SUBJECT:
                    for (int i = 0; i < messages.size(); i++)
                        if (messages.get(i).getSubject().contains(value))
                            ruleHandler.applyRule(messages, i, folder);
                    break;
            }
        }

        // ----- Saving changes to messages in database -----
        List<Message> folderMessagesAfterUpdate = folder.getMessages();

        // ----- Saving new messages in the folder -----
        for (Message folderMessageAfterUpdate : folderMessagesAfterUpdate) {
            if (!folderMessagesBeforeUpdate.contains(folderMessageAfterUpdate)) {
                messageService.save(folderMessageAfterUpdate);
            }
        }

        // ----- Deleting messages from the folder -----
        for (Message folderMessageBeforeUpdate : folderMessagesBeforeUpdate) {
            if (!folderMessagesAfterUpdate.contains(folderMessageBeforeUpdate)) {
                messageService.remove(folderMessageBeforeUpdate.getId());
            }
        }

        return new ResponseEntity<String>("Finished applying rules!", HttpStatus.OK);
    }
}

