package uns.ac.rs.MailApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.Folder;


public interface FolderRepository extends JpaRepository<Folder, Integer> {

	public List<Folder> findAllByAccountAndParentFolderIsNull(Account account);
	
	public Folder findByNameAndAccount(String name, Account account);
	
	public List<Folder> findAllByAccount(Account account);
	
	public Folder findByIdAndAccount(Integer id, Account account);
	
}
