package uns.ac.rs.MailApi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "rules")
public class Rule {

	// ----- ENUMERATIONS
	public enum RuleCondition {
		TO,
		FROM,
		CC,
		SUBJECT
	}
	
	public enum RuleOperation {
		MOVE,
		COPY,
		DELETE
	}

	// ----- FIELDS
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
	private Integer id;
	
	@Enumerated
	@Column(columnDefinition = "BIGINT", name = "rule_condition", nullable = false)
	private RuleCondition condition;
	
	@Column(columnDefinition = "VARCHAR(100)", name = "value", nullable = false)
	private String value;
	
	@Enumerated
	@Column(columnDefinition = "BIGINT", name = "rule_operation", nullable = false)
	private RuleOperation operation; 
	
	@ManyToOne
	@JoinColumn(name = "folder", referencedColumnName = "id", nullable = true)
	private Folder folder;

	// ----- GETTERS AND SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RuleCondition getCondition() {
		return condition;
	}

	public void setCondition(RuleCondition condition) {
		this.condition = condition;
	}

	public String getConditionValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public RuleOperation getOperation() {
		return operation;
	}

	public void setOperation(RuleOperation operation) {
		this.operation = operation;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public String getValue() {
		return value;
	}

}