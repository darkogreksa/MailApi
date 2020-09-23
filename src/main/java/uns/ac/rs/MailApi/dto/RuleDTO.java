package uns.ac.rs.MailApi.dto;

import uns.ac.rs.MailApi.entity.Rule;

public class RuleDTO {
    // ----- FIELDS
    private Integer id;
    private Rule.RuleCondition condition;
    private String value;
    private Rule.RuleOperation operation;

    public RuleDTO(Rule rule) {
        super();
        this.id = rule.getId();
        this.condition = rule.getCondition();
        this.value = rule.getValue();
        this.operation = rule.getOperation();
    }

    public RuleDTO() {

    }

    // ----- GETTERS AND SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Rule.RuleCondition getCondition() {
        return condition;
    }

    public void setCondition(Rule.RuleCondition condition) {
        this.condition = condition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Rule.RuleOperation getOperation() {
        return operation;
    }

    public void setOperation(Rule.RuleOperation operation) {
        this.operation = operation;
    }

}

