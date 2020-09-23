package uns.ac.rs.MailApi.service;

import java.util.List;

import uns.ac.rs.MailApi.entity.Contact;
import uns.ac.rs.MailApi.entity.Photo;


public interface PhotoServiceInterface {

	public List<Photo> findAll();
	
	public List<Photo> getPhotos(Contact contact);
	
	
	public Photo findOne(Integer photoId);
	

}
