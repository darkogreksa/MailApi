package uns.ac.rs.MailApi.entity;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "folders")
public class Folder {

	// ----- FIELDS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "parent_folder", referencedColumnName = "id", nullable = true)
	private Folder parentFolder;
	
	@OneToMany(cascade = { REMOVE, PERSIST }, fetch = LAZY, mappedBy = "parentFolder")
	private List<Folder> subFolders;
	
	@OneToMany(cascade = { REMOVE, PERSIST }, fetch = LAZY, mappedBy = "folder", orphanRemoval = true)
    private List<Rule> rules;

    @OneToMany(cascade = { REMOVE }, fetch = EAGER, mappedBy = "folder")
	private List<Message> messages;
	
	public Folder() {
		
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

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	public List<Folder> getSubFolders() {
		return subFolders;
	}

	public void setSubFolders(List<Folder> subFolders) {
		this.subFolders = subFolders;
	}
	
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void fixRules() {
		for (Rule rule : rules) {
			rule.setFolder(this);
		}
	}

	@Override
	public String toString() {
		return "Folder [id=" + id + ", name=" + name + ", account=" + account
				+ ", subFolders=" + subFolders + ", rules=" + rules + "]";
	}
	
}

