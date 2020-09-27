package uns.ac.rs.MailApi.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uns.ac.rs.MailApi.entity.Rule.RuleCondition;
import uns.ac.rs.MailApi.entity.Rule.RuleOperation;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rule.class)
public abstract class Rule_ {

	public static volatile SingularAttribute<Rule, RuleCondition> condition;
	public static volatile SingularAttribute<Rule, Folder> folder;
	public static volatile SingularAttribute<Rule, Integer> id;
	public static volatile SingularAttribute<Rule, String> value;
	public static volatile SingularAttribute<Rule, RuleOperation> operation;

}

