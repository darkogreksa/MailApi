package uns.ac.rs.MailApi.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Folder.class)
public abstract class Folder_ {

	public static volatile SingularAttribute<Folder, Folder> parentFolder;
	public static volatile ListAttribute<Folder, Folder> subFolders;
	public static volatile SingularAttribute<Folder, String> name;
	public static volatile ListAttribute<Folder, Message> messages;
	public static volatile ListAttribute<Folder, Rule> rules;
	public static volatile SingularAttribute<Folder, Integer> id;
	public static volatile SingularAttribute<Folder, Account> account;

}

