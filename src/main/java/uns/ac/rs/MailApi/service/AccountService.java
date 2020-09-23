package uns.ac.rs.MailApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.User;
import uns.ac.rs.MailApi.repository.AccountRepository;

import java.security.Principal;
import java.util.List;


@Service
public class AccountService implements AccountServiceInterface {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserService userService;
	
	@Override
	public Account findOne(Integer id) {
		return accountRepository.getOne(id);
	}
	
	@Override
	public List<Account> findByUser(User user) {
		return accountRepository.findByUser(user);
	}

	@Override
	public Account findByIdAndUser(Integer id, User user) {
		return accountRepository.findByIdAndUser(id, user);
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public void remove(Integer id) {
		accountRepository.deleteById(id);
	}
	
	/**
	 * Method that returns an Account for a given principal
	 * and account index.
	 * 
	 * @param Principal object passed from Spring Security.
	 * @param Index of the account that a user owns.
	 * @return Account that is referenced in the URL or null if
	 * no Account is found.
	 **/
	public Account resolveAccount(Principal principal, int accountIndex) {
		User user = userService.findByUsername(principal.getName());

		List<Account> userAccounts = user.getAccounts();
		
		Account account;
		
		try {
			account = userAccounts.get(accountIndex);
			return account;
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

}
