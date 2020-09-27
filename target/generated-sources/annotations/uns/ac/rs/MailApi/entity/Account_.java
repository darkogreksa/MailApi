package uns.ac.rs.MailApi.entity;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Account.class)
public abstract class Account_ {

	public static volatile SingularAttribute<Account, String> password;
	public static volatile ListAttribute<Account, Folder> folders;
	public static volatile SingularAttribute<Account, Timestamp> lastSyncTime;
	public static volatile SingularAttribute<Account, Integer> inServerPort;
	public static volatile SingularAttribute<Account, String> displayName;
	public static volatile SingularAttribute<Account, Integer> smtpPort;
	public static volatile SingularAttribute<Account, String> inServerAddress;
	public static volatile SingularAttribute<Account, String> smtpAddress;
	public static volatile SingularAttribute<Account, Integer> id;
	public static volatile SingularAttribute<Account, Integer> inServerType;
	public static volatile SingularAttribute<Account, User> user;
	public static volatile SingularAttribute<Account, String> username;

}

