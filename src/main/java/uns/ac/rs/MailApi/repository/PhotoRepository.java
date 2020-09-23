package uns.ac.rs.MailApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.MailApi.entity.Contact;
import uns.ac.rs.MailApi.entity.Photo;


public interface PhotoRepository extends JpaRepository<Photo, Integer>{
	
	
	public List<Photo> findAll();
	
	public List<Photo> findByContact(Integer contactId);
	
	public List<Photo> findAllByContact(Contact contact);
	
	

}
