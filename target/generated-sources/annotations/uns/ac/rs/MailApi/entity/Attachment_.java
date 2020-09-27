package uns.ac.rs.MailApi.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Attachment.class)
public abstract class Attachment_ {

	public static volatile SingularAttribute<Attachment, String> data;
	public static volatile SingularAttribute<Attachment, String> name;
	public static volatile SingularAttribute<Attachment, Integer> id;
	public static volatile SingularAttribute<Attachment, String> mimeType;
	public static volatile SingularAttribute<Attachment, Message> message;

}

