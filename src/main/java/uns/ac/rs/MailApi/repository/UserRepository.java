package uns.ac.rs.MailApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.MailApi.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	
}
