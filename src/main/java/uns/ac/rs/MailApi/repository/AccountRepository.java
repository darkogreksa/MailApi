package uns.ac.rs.MailApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.User;


public interface AccountRepository extends JpaRepository<Account, Integer> {

	public List<Account> findByUser(User user);
	
	public Account findByIdAndUser(Integer id, User user);
	
}
