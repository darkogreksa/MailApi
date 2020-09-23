package uns.ac.rs.MailApi.service;

import java.util.List;

import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.Folder;


public interface FolderServiceInterface {
	
	public Folder findOne(Integer id, Account account);
	
	public Folder findDraft(Account account);
	
	public Folder findInbox(Account account);
	
	public Folder findOutbox(Account account);
	
	public List<Folder> findAll(Account account);
	
	public Folder save(Folder folder);
	
	public void remove(Integer id);

}