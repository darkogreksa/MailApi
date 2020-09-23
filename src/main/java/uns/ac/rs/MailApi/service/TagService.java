package uns.ac.rs.MailApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.MailApi.entity.Tag;
import uns.ac.rs.MailApi.entity.User;
import uns.ac.rs.MailApi.repository.TagRepository;

import java.util.List;

@Service
public class TagService implements TagServiceInterface{
	
	@Autowired
	private TagRepository tagRepository;
	
	@Override
	public Tag findOne(Integer id, User user) {
		return tagRepository.findByIdAndUser(id, user);
	}
	
	@Override
	public List<Tag> findAll(){
		return tagRepository.findAll();
	}
	
	@Override
	public Tag save(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public void remove(Integer id) {
		tagRepository.deleteById(id);
	}
}
