package uns.ac.rs.MailApi.dto;

import uns.ac.rs.MailApi.entity.Attachment;

public class AttachmentDTO {

    private Integer id;
    private String data;
    private String mimeType;
    private String name;

    public AttachmentDTO(Attachment attachment) {
        super();
        this.id = attachment.getId();
        this.data = attachment.getData();
        this.mimeType = attachment.getMimeType();
        this.name = attachment.getName();
    }

    public AttachmentDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

