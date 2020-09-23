package uns.ac.rs.MailApi.service;

import java.util.List;

import uns.ac.rs.MailApi.entity.Tag;
import uns.ac.rs.MailApi.entity.User;

public interface TagServiceInterface {

	public Tag findOne(Integer id, User user);
	
	public List<Tag> findAll();
	
	public Tag save(Tag tag);
	
	public void remove(Integer id);
}
