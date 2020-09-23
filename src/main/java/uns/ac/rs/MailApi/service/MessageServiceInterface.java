package uns.ac.rs.MailApi.service;

import java.util.Date;
import java.util.List;

import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.Message;


public interface MessageServiceInterface {

	public Message findOne(Integer id, Account acconut);

	public List<Message> findAll(Account account);
	
	public List<Message> getMessages(Account account);

	/**
	 * @deprecated This was used for Android for notifications.
	 */
	@Deprecated
	public List<Message> findAllUnread(Account account);

	public Date findLastDate(Account account);

	public Message save(Message message);

	public void remove(Integer id);
	
	public List<Message> findByFrom(String userEmail);
	

}
