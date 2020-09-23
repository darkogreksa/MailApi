package uns.ac.rs.MailApi.dto;

import uns.ac.rs.MailApi.entity.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagDTO {

    private Integer id;
    private String name;
    private List<MessageDTO> messages = new ArrayList<MessageDTO>();
    private UserDTO user;

    public TagDTO() {

    }

    public TagDTO(Tag tag) {
        super();
        this.id = tag.getId();
        this.name = tag.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
