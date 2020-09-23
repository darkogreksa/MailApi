package uns.ac.rs.MailApi.service;

import java.util.List;

import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Rule;


public interface RuleServiceInterface {

	public Rule findOne(Integer ruleId);

	public Rule findByIdAndFolder(Integer id, Folder folder);
	
	public List<Rule> findByFolder(Folder folder);

	public List<Rule> findAll();

	public Rule save(Rule rule);

	public void remove(Integer id);

}
