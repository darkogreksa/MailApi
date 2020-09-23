package uns.ac.rs.MailApi.service;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.MailApi.entity.Contact;
import uns.ac.rs.MailApi.entity.User;
import uns.ac.rs.MailApi.repository.ContactRepository;

@Service
public class ContactService implements ContactServiceInterface {

	@Autowired
	private ContactRepository contactRepository;
	

	@Autowired
	private UserService userService;

	@Override
	public Contact findOne(Integer contactId, User user) {
		return contactRepository.findByIdAndUser(contactId, user);
	}

	@Override
	public Contact save(Contact contact) {
		return contactRepository.save(contact);
	}

	@Override
	public void remove(Integer id) {
		contactRepository.deleteById(id);

	}

	@Override
	public List<Contact> findAllContacts(User user) {
		return contactRepository.findAllByUser(user);

	}
	
	
	public Contact findContact(Principal principal,int contactId) {
		User user = userService.findByUsername(principal.getName());
		List<Contact> userContacts = user.getContacts();
		Contact contact = null;
		try {
			contact = userContacts.get(contactId);
			return contact;
		}catch (IndexOutOfBoundsException ex) {
			return null;
		}
		
		
	}


}
