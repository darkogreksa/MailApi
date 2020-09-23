package uns.ac.rs.MailApi.dto;

public class BackendContactDTO {

    private String email;

    public BackendContactDTO() {

    }

    public BackendContactDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

