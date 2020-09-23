package uns.ac.rs.MailApi.dto;

import uns.ac.rs.MailApi.entity.Attachment;
import uns.ac.rs.MailApi.entity.Message;
import uns.ac.rs.MailApi.entity.Tag;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MessageDTO {

    public MessageDTO() {

    }

    private Integer id;
    private BackendContactDTO from;
    private List<BackendContactDTO> to = new ArrayList<BackendContactDTO>();
    private List<BackendContactDTO> cc = new ArrayList<BackendContactDTO>();
    private List<BackendContactDTO> bcc = new ArrayList<BackendContactDTO>();
    private Timestamp dateTime;
    private String subject;
    private String content;
    private boolean unread;
    private FolderDTO folder;
    private List<AttachmentDTO> attachments = new ArrayList<AttachmentDTO>();
    private List<TagDTO> tags = new ArrayList<TagDTO>();

    public MessageDTO(Message message) {
        super();
        this.id = message.getId();
        this.from = new BackendContactDTO(message.getFrom());

        StringTokenizer tokenizer;

        if (message.getTo() != null) {
            tokenizer = new StringTokenizer(message.getTo(), ",");
            while (tokenizer.hasMoreElements()) {
                this.to.add(new BackendContactDTO(tokenizer.nextToken()));
            }
        }

        if (message.getCc() != null) {
            tokenizer = new StringTokenizer(message.getCc(), ",");
            while (tokenizer.hasMoreElements()) {
                this.cc.add(new BackendContactDTO(tokenizer.nextToken()));
            }
        }

        if (message.getBcc() != null) {
            tokenizer = new StringTokenizer(message.getBcc(), ",");
            while (tokenizer.hasMoreElements()) {
                this.bcc.add(new BackendContactDTO(tokenizer.nextToken()));
            }
        }

        this.dateTime = message.getDateTime();
        this.subject = message.getSubject();
        this.content = message.getContent();
        this.unread = message.getUnread();

        this.folder = new FolderDTO();
        this.folder.setId(message.getFolder().getId());
        this.folder.setName(message.getFolder().getName());

        for (Attachment attachment : message.getAttachments()) {
            attachments.add(new AttachmentDTO(attachment));
        }

        for (Tag tag : message.getTags()) {
            tags.add(new TagDTO(tag));
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BackendContactDTO getFrom() {
        return from;
    }

    public List<BackendContactDTO> getTo() {
        return to;
    }

    public List<BackendContactDTO> getCc() {
        return cc;
    }

    public List<BackendContactDTO> getBcc() {
        return bcc;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public List<AttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentDTO> attachments) {
        this.attachments = attachments;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public static String contactsToString(List<BackendContactDTO> contacts) {
        StringBuilder builder = new StringBuilder();
        for (BackendContactDTO contact : contacts) {
            builder.append(contact.getEmail() + ", ");
        }

        int length = builder.length();


        return (length > 0) ? builder.toString().substring(0, length-2) : builder.toString();
    }

    public FolderDTO getFolder() {
        return folder;
    }

    public void setFolder(FolderDTO folder) {
        this.folder = folder;
    }

    public void setFrom(BackendContactDTO from) {
        this.from = from;
    }

    public void setTo(List<BackendContactDTO> to) {
        this.to = to;
    }

    public void setCc(List<BackendContactDTO> cc) {
        this.cc = cc;
    }

    public void setBcc(List<BackendContactDTO> bcc) {
        this.bcc = bcc;
    }

}

