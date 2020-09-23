package uns.ac.rs.MailApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Message;
import uns.ac.rs.MailApi.repository.MessageRepository;

import java.util.Date;
import java.util.List;

@Service
public class MessageService implements MessageServiceInterface{
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private FolderService folderService;

	@Override
	public Message findOne(Integer id, Account account) {
		return messageRepository.findByIdAndAccount(id, account);
	}
	
	@Override
	public List<Message> findByFrom(String userEmail) {
		return messageRepository.findByFromContaining(userEmail);
	}
	
	
	@Override 
	public List<Message> findAll(Account account){
		return messageRepository.findAllByAccount(account);
	}
	
	/**
	 * Loads all messages stored in inbox folder for an Account.
	 * 
	 * @param Account for which loading is done.
	 */
	@Override
	public List<Message> getMessages(Account account) {
		Folder folder = folderService.findInbox(account);
		
		return messageRepository.findAllByAccountAndFolder(account, folder);
	}
	
	@Override 
	public List<Message> findAllUnread(Account account){
		return messageRepository.findAllByUnreadIsTrue();
	}
	
	@Override
	public Message save(Message message) {
		return messageRepository.save(message);
	}
	
	@Override
	public void remove(Integer id) {
		messageRepository.deleteById(id);
	}
	
	@Override
	public Date findLastDate(Account account) {
		return messageRepository.findLastDate(account.getId());
	}


}
