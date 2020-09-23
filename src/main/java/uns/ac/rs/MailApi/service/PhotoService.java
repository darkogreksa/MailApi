package uns.ac.rs.MailApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.MailApi.entity.Contact;
import uns.ac.rs.MailApi.entity.Photo;
import uns.ac.rs.MailApi.repository.PhotoRepository;

import java.util.List;

@Service
public class PhotoService  implements PhotoServiceInterface {

	@Autowired
	private PhotoRepository photoRepository;
	
	@Override
	public List<Photo> findAll() {
		return photoRepository.findAll();
	}

	@Override
	public List<Photo> getPhotos(Contact contact) {
		return photoRepository.findAllByContact(contact);
	}

	@Override
	public Photo findOne(Integer photoId) {
		return photoRepository.getOne(photoId);
	}


	
	
}
