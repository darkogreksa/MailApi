package uns.ac.rs.MailApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Rule;


public interface RuleRepository extends JpaRepository<Rule, Integer> {

	public Rule findByIdAndFolder(Integer id, Folder folder);
	
	public List<Rule> findAllByFolder(Folder folder);
	
}
