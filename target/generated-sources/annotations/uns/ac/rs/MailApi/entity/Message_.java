package uns.ac.rs.MailApi.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, String> cc;
	public static volatile SingularAttribute<Message, Timestamp> dateTime;
	public static volatile SingularAttribute<Message, String> bcc;
	public static volatile ListAttribute<Message, Attachment> attachments;
	public static volatile SingularAttribute<Message, Boolean> unread;
	public static volatile SingularAttribute<Message, String> subject;
	public static volatile SingularAttribute<Message, String> content;
	public static volatile ListAttribute<Message, Tag> tags;
	public static volatile SingularAttribute<Message, Folder> folder;
	public static volatile SingularAttribute<Message, String> from;
	public static volatile SingularAttribute<Message, Integer> id;
	public static volatile SingularAttribute<Message, String> to;
	public static volatile SingularAttribute<Message, Account> account;

}

