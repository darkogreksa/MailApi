package uns.ac.rs.MailApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uns.ac.rs.MailApi.dto.PhotoDTO;
import uns.ac.rs.MailApi.entity.Contact;
import uns.ac.rs.MailApi.entity.Photo;
import uns.ac.rs.MailApi.service.ContactService;
import uns.ac.rs.MailApi.service.PhotoService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("contacts/{id}/photos")

public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<?> getAllPhotos(@PathVariable("id") int contactId, Principal principal) {

        if(contactId < 0) {
            return new ResponseEntity<>("You must provide an ID for an contact!", HttpStatus.BAD_REQUEST);
        }

        Contact contact = contactService.findContact(principal, contactId);
        if(contact == null) {
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);
        }

        List<PhotoDTO> photos = new ArrayList<PhotoDTO>();
        for(Photo photo : photoService.getPhotos(contact)) {
            photos.add(new PhotoDTO(photo));

        }

        return new ResponseEntity<List<PhotoDTO>>(photos,HttpStatus.OK);


    }


    @PostMapping("/{photo_id}")
    public ResponseEntity<?> uploadPhoto(@PathVariable("id") int contactId, @PathVariable("photo_id") int photoId, @RequestParam("upload_photo") MultipartFile photo, Principal principal) {


        if(contactId < 0) {
            return new ResponseEntity<>("You must provide an ID for an contact!", HttpStatus.BAD_REQUEST);
        }

        if(photo.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        Contact contact = contactService.findContact(principal, contactId);
        if(contact == null) {
            return new ResponseEntity<>("Unexisting contact.", HttpStatus.NOT_FOUND);
        }

        Photo existingPhoto = photoService.findOne(photoId);
        if(existingPhoto == null) {
            return new ResponseEntity<>("Unexisting photo.", HttpStatus.NOT_FOUND);

        }

        try {
            byte[] bytes = photo.getBytes();




        }catch(Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }



}

