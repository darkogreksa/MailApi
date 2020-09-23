package uns.ac.rs.MailApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.MailApi.dto.TagDTO;
import uns.ac.rs.MailApi.entity.Message;
import uns.ac.rs.MailApi.entity.Tag;
import uns.ac.rs.MailApi.entity.User;
import uns.ac.rs.MailApi.service.TagService;
import uns.ac.rs.MailApi.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<TagDTO>> getAllTags(){
        List<TagDTO> tags = new ArrayList<TagDTO>();

        for(Tag tag : tagService.findAll()) {
            tags.add(new TagDTO(tag));
        }

        return new ResponseEntity<List<TagDTO>>(tags, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(
            @PathVariable("id") Integer tagId,
            Principal principal)
    {
        User user = userService.findByUsername(principal.getName());
        // ----- Obtaining data from database -----
        Tag tag = tagService.findOne(tagId,user);

        if(tag == null) {
            return new ResponseEntity<String>("Unexisting tag.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<TagDTO>(new TagDTO(tag),HttpStatus.OK);



    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> createTag(
            @RequestBody TagDTO tagDTO,
            Principal principal)
    {
        // ----- Request data validation -----
        if (tagDTO.getName() == null || tagDTO.getName().isEmpty())
            return new ResponseEntity<>("You must provide a name for your tag!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user -----
        User user = userService.findByUsername(principal.getName());

        // ----- Setting data from frontend -----
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());
        tag.setMessages(new ArrayList<Message>());
        tag.setUser(user);

        // ----- Saving to database -----
        tagService.save(tag);

        //return new ResponseEntity<TagDTO>(new TagDTO(tag), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<?> updateTag(
            @RequestBody TagDTO tagDTO,
            Principal principal)
    {
        // ----- Request data validation -----
        if (tagDTO.getId() == null || tagDTO.getId() == 0)
            return new ResponseEntity<>("You must provide an ID for your tag!", HttpStatus.BAD_REQUEST);
        else if (tagDTO.getName() == null || tagDTO.getName().isEmpty())
            return new ResponseEntity<>("You must provide a name for your tag!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user -----
        User user = userService.findByUsername(principal.getName());

        // ----- Obtaining data from database -----
        Tag tag = tagService.findOne(tagDTO.getId(), user);
        if (tag == null)
            return new ResponseEntity<String>("Unexisting tag.", HttpStatus.NOT_FOUND);

        // ----- Setting data from frontend -----
        tag.setName(tagDTO.getName());

        // ----- Saving to database -----
        tagService.save(tag);

        //return new ResponseEntity<TagDTO>(new TagDTO(tag), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @DeleteMapping
    public ResponseEntity<?> deleteTag(
            @RequestBody TagDTO tagDTO,
            Principal principal)
    {
        // ----- Request data validation -----
        if (tagDTO.getId() == null || tagDTO.getId() == 0)
            return new ResponseEntity<>("You must provide an ID for your tag!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user -----
        User user = userService.findByUsername(principal.getName());

        // ----- Obtaining data from database -----
        Tag tag = tagService.findOne(tagDTO.getId(), user);
        if (tag == null)
            return new ResponseEntity<String>("Unexisting tag.", HttpStatus.NOT_FOUND);

        // ----- Removing from database -----
        tagService.remove(tag.getId());

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}

