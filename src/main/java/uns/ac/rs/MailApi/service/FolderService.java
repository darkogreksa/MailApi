package uns.ac.rs.MailApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.repository.FolderRepository;

@Service
public class FolderService implements FolderServiceInterface {

	@Autowired
	private FolderRepository folderRepository;

	@Override
	public Folder findOne(Integer id, Account account) {
		return folderRepository.findByIdAndAccount(id, account);
	}
	
	@Override
	public Folder findInbox(Account account) {
		return folderRepository.findByNameAndAccount("Inbox", account);
	}
	
	@Override
	public Folder findDraft(Account account) {
		return folderRepository.findByNameAndAccount("Drafts", account);
	}
	
	@Override
	public Folder findOutbox(Account account) {
		return folderRepository.findByNameAndAccount("Outbox", account);
	}

	@Override
	public List<Folder> findAll(Account account) {
		return folderRepository.findAllByAccount(account);
	}
	
	public List<Folder> findRootFolders(Account account) {
		return folderRepository.findAllByAccountAndParentFolderIsNull(account);
	}

	@Override
	public Folder save(Folder folder) {
		return folderRepository.save(folder);
	}

	@Override
	public void remove(Integer id) {
		folderRepository.deleteById(id);
	}
}
