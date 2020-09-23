package uns.ac.rs.MailApi.dto;

import uns.ac.rs.MailApi.entity.Contact;
import uns.ac.rs.MailApi.entity.Photo;

import java.util.ArrayList;
import java.util.List;

public class ContactDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String displayName;
    private String email;
    private String note;
    private List<PhotoDTO> photos = new ArrayList<PhotoDTO>();

    public ContactDTO(Contact contact) {
        super();
        this.id = contact.getId();
        this.firstName = contact.getFirstName();
        this.lastName = contact.getLastName();
        this.displayName = contact.getDisplayName();
        this.email = contact.getEmail();
        this.note = contact.getNote();

        for (Photo photo : contact.getPhotos()) {
            photos.add(new PhotoDTO(photo));
        }

    }

    public ContactDTO() {

    }

    public ContactDTO(String email) {
        super();
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos) {
        this.photos = photos;
    }

}

