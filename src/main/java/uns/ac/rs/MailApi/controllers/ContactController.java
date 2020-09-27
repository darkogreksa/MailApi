package uns.ac.rs.MailApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.MailApi.dto.ContactDTO;
import uns.ac.rs.MailApi.entity.Contact;
import uns.ac.rs.MailApi.entity.User;
import uns.ac.rs.MailApi.lucene.Indexer;
import uns.ac.rs.MailApi.lucene.model.IndexUnit;
import uns.ac.rs.MailApi.service.ContactService;
import uns.ac.rs.MailApi.service.PhotoService;
import uns.ac.rs.MailApi.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;


    @Autowired
    private PhotoService photoService;


    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts(Principal principal) {

        User user = userService.findByUsername(principal.getName());

        List<ContactDTO> contacts = new ArrayList<>();

        for (Contact contact : contactService.findAllContacts(user)) {
            contacts.add(new ContactDTO(contact));
        }

        return new ResponseEntity<List<ContactDTO>>(contacts, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> getContactById(
            @PathVariable("id") Integer contactId,
            Principal principal)
    {
        User user = userService.findByUsername(principal.getName());

        Contact contact = contactService.findOne(contactId, user);

        if (contact == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> saveContact(@RequestBody ContactDTO contact,Principal principal) {

        User user = userService.findByUsername(principal.getName());

        Contact nContact = new Contact();
        nContact.setFirstName(contact.getFirstName());
        nContact.setLastName(contact.getLastName());
        nContact.setDisplayName(contact.getDisplayName());
        nContact.setEmail(contact.getEmail());
        nContact.setNote(contact.getNote());
        nContact.setPhotos(new ArrayList<>());
        nContact.setUser(user);

        IndexUnit indexUnit = new IndexUnit();
        indexUnit.setContactName(contact.getFirstName());
        indexUnit.setConcactLastName(contact.getLastName());
        indexUnit.setContactNote(contact.getNote());
        Indexer.getInstance().add(indexUnit.getLuceneDocument());

        nContact = contactService.save(nContact);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<?> updateContact(@RequestBody ContactDTO contact,Principal principal) {

        User user = userService.findByUsername(principal.getName());

        Contact econtact = contactService.findOne(contact.getId(), user);

        if(econtact!=null) {
            econtact.setDisplayName(contact.getDisplayName());
            econtact.setFirstName(contact.getFirstName());
            econtact.setLastName(contact.getLastName());
            econtact.setNote(contact.getNote());
            econtact.setEmail(contact.getEmail());
            econtact.setUser(user);
            econtact = contactService.save(econtact);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>("You are unauthorized to do that.", HttpStatus.UNAUTHORIZED);
        }

    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(
            @PathVariable("id") Integer contactId,
            Principal principal)
    {
        User user = userService.findByUsername(principal.getName());

        Contact contact = contactService.findOne(contactId, user);

        if (contact != null) {
            contactService.remove(contactId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

}

