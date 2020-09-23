package uns.ac.rs.MailApi.dto;

import uns.ac.rs.MailApi.entity.Photo;

public class PhotoDTO {

    private Integer id;
    private String path;

    public PhotoDTO(Photo photo) {
        super();
        this.id = photo.getId();
        this.path = photo.getPath();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}

