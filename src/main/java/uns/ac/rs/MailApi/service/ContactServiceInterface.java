package uns.ac.rs.MailApi.service;

import java.util.List;

import uns.ac.rs.MailApi.entity.Contact;
import uns.ac.rs.MailApi.entity.User;

public interface ContactServiceInterface {

	public List<Contact> findAllContacts(User user);

	public Contact findOne(Integer contactId, User user);

	public Contact save(Contact contact);

	public void remove(Integer id);

}
