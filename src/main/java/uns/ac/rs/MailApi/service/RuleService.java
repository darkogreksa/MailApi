package uns.ac.rs.MailApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Rule;
import uns.ac.rs.MailApi.repository.RuleRepository;

import java.util.List;

@Service
public class RuleService implements RuleServiceInterface {
	
	@Autowired
	RuleRepository ruleRepository;

	@Override
	public Rule findOne(Integer ruleId) {
		return ruleRepository.getOne(ruleId); 
	}

	@Override
	public Rule findByIdAndFolder(Integer id, Folder folder) {
		return ruleRepository.findByIdAndFolder(id, folder); 
	}
	
	@Override
	public List<Rule> findByFolder(Folder folder) {
		return ruleRepository.findAllByFolder(folder);
	}

	@Override
	public List<Rule> findAll() {
		return ruleRepository.findAll();
	}

	@Override
	public Rule save(Rule rule) {
		return ruleRepository.save(rule);
	}

	@Override
	public void remove(Integer id) {
		ruleRepository.deleteById(id);
	}

	
	
}
