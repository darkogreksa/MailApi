package uns.ac.rs.MailApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.MailApi.entity.Contact;
import uns.ac.rs.MailApi.entity.User;


public interface ContactRepository extends JpaRepository<Contact, Integer> {
	
	public List<Contact> findAllByUser(User user);
	
	public Contact findByIdAndUser(Integer id,User user);
	


}
