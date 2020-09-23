package uns.ac.rs.MailApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.MailApi.entity.Tag;
import uns.ac.rs.MailApi.entity.User;


public interface TagRepository extends JpaRepository<Tag, Integer>{

	public Tag findByIdAndUser(Integer id, User user);
	
}
