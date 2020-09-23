package uns.ac.rs.MailApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uns.ac.rs.MailApi.dto.AttachmentDTO;
import uns.ac.rs.MailApi.entity.Attachment;
import uns.ac.rs.MailApi.service.AttachmentService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping
    public ResponseEntity<List<AttachmentDTO>> getAllAttachments(){
        List<AttachmentDTO> attachments = new ArrayList<AttachmentDTO>();

        for(Attachment attachment : attachmentService.findAll()) {
            attachments.add(new AttachmentDTO(attachment));
        }

        return new ResponseEntity<List<AttachmentDTO>>(attachments, HttpStatus.OK);
    }
}
