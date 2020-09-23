package uns.ac.rs.MailApi.service;

import java.util.List;

import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.User;


public interface AccountServiceInterface {
	
	public Account findOne(Integer id);
	
	public List<Account> findByUser(User user);
	
	public Account findByIdAndUser(Integer id, User user);
	
	public List<Account> findAll();
	
	public Account save(Account account);
	
	public void remove(Integer id);
	
}
