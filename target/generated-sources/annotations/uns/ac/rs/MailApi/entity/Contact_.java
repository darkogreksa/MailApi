package uns.ac.rs.MailApi.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contact.class)
public abstract class Contact_ {

	public static volatile SingularAttribute<Contact, String> firstName;
	public static volatile SingularAttribute<Contact, String> lastName;
	public static volatile SingularAttribute<Contact, String> note;
	public static volatile SingularAttribute<Contact, String> displayName;
	public static volatile SingularAttribute<Contact, Integer> id;
	public static volatile SingularAttribute<Contact, User> user;
	public static volatile ListAttribute<Contact, Photo> photos;
	public static volatile SingularAttribute<Contact, String> email;

}

