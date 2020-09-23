package uns.ac.rs.MailApi.service;

import java.util.List;

import uns.ac.rs.MailApi.entity.Attachment;


public interface AttachmentServiceInterface {

	public Attachment findOne(Integer id);
	
	public List<Attachment> findAll();
	
	public Attachment save(Attachment attachment);
	
	public void remove(Integer id);
}
