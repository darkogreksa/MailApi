package uns.ac.rs.MailApi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "attachments")
public class Attachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;

	//BASE64 IN COLUMN DEFINITION ???
	@Column(name = "data", columnDefinition = "LONGTEXT", nullable = false)
	private String data;

	@Column(name = "mime_type", columnDefinition = "VARCHAR(20)", nullable = false)
	private String mimeType;

	@Column(name = "name", columnDefinition = "VARCHAR(250)", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "message", referencedColumnName = "id", nullable = false)
	private Message message;

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

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
