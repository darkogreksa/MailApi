package uns.ac.rs.MailApi.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import uns.ac.rs.MailApi.entity.User;


public interface UserServiceInterface extends UserDetailsService {

	public User findOne(Integer id);

	public User findByUsername(String username);

	public List<User> findAll();

	public User save(User account);

	public void remove(Integer id);

}
