package uns.ac.rs.MailApi.entity;

import static javax.persistence.CascadeType.ALL;
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
@Table(name = "contacts")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;

	@Column(name = "first_name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String firstName;

	@Column(name = "last_name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String lastName;

	@Column(name = "display_name", columnDefinition = "VARCHAR(100)", nullable = false)
	private String displayName;

	@Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
	private String email;

	@Column(name = "note", columnDefinition = "TEXT", nullable = false)
	private String note;

	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
	private User user;

	@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "contact")
	private List<Photo> photos;

	public Contact() {
		super();
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

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

}
