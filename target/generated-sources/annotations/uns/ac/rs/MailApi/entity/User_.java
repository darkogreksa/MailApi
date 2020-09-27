package uns.ac.rs.MailApi.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile ListAttribute<User, Account> accounts;
	public static volatile ListAttribute<User, Contact> contacts;
	public static volatile ListAttribute<User, Authority> authorities;
	public static volatile SingularAttribute<User, String> username;
	public static volatile ListAttribute<User, Tag> tags;

}

