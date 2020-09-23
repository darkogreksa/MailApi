package uns.ac.rs.MailApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uns.ac.rs.MailApi.entity.User;
import uns.ac.rs.MailApi.repository.UserRepository;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findOne(Integer id) {
		return userRepository.getOne(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void remove(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Bad credentials.");
		} else if (user.getAuthorities().size() == 0) {
			throw new UsernameNotFoundException("Unathorized.");
		}
		
		return user;
	}

}
