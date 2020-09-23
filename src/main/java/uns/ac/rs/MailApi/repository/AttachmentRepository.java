package uns.ac.rs.MailApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uns.ac.rs.MailApi.entity.Attachment;


public interface AttachmentRepository extends JpaRepository<Attachment, Integer>{

}
