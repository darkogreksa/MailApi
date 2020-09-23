package uns.ac.rs.MailApi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Message;


public interface MessageRepository extends JpaRepository<Message, Integer>{

	public Message findByIdAndAccount(Integer id, Account account);
	
	public List<Message> findAllByUnreadIsTrue();
	
	public List<Message> findAllByAccount(Account account);
	
	public List<Message> findAllByAccountAndFolder(Account account, Folder folder);
	
	@Query(value = "SELECT MAX(date_time) FROM messages WHERE account = :accountId", nativeQuery = true)
	public Date findLastDate(@Param("accountId") int accountId);
	
	public List<Message> findByFromContaining(String userEmail);
	}
