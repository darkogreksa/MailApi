package uns.ac.rs.MailApi.dto;

import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Message;
import uns.ac.rs.MailApi.entity.Rule;

import java.util.ArrayList;
import java.util.List;

public class FolderDTO {
    // ----- FIELDS
    private Integer id;
    private String name;
    private FolderDTO parentFolder;
    private List<FolderDTO> subFolders = new ArrayList<FolderDTO>();
    private List<RuleDTO> rules = new ArrayList<RuleDTO>();
    private List<MessageDTO> messages = new ArrayList<MessageDTO>();

    public FolderDTO() {

    }

    public FolderDTO(Folder folder) {
        super();
        this.id = folder.getId();
        this.name = folder.getName();

        Folder parentFolder = folder.getParentFolder();
        if (parentFolder != null) {
            this.parentFolder = new FolderDTO();
            this.parentFolder.setId(parentFolder.getId());
            this.parentFolder.setName(parentFolder.getName());
            this.parentFolder.setRules(new ArrayList<RuleDTO>());
            this.parentFolder.setSubFolders(new ArrayList<FolderDTO>());
        }

        for (Folder subFolder : folder.getSubFolders()) {
            FolderDTO subFolderDTO = new FolderDTO();
            subFolderDTO.setId(subFolder.getId());
            subFolderDTO.setName(subFolder.getName());
            subFolderDTO.setRules(new ArrayList<RuleDTO>());
            subFolderDTO.setSubFolders(new ArrayList<FolderDTO>());
            subFolders.add(subFolderDTO);
        }

        for (Rule rule : folder.getRules()) {
            rules.add(new RuleDTO(rule));
        }

        for (Message message : folder.getMessages()) {
            messages.add(new MessageDTO(message));
        }
    }

    // ----- GETTERS AND SETTERS
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

    public List<RuleDTO> getRules() {
        return rules;
    }

    public void setRules(List<RuleDTO> rules) {
        this.rules = rules;
    }

    public FolderDTO getParentFolder() {
        return parentFolder;
    }

    public List<FolderDTO> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<FolderDTO> subFolders) {
        this.subFolders = subFolders;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

}

