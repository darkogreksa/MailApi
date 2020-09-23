package uns.ac.rs.MailApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.Contact;
import uns.ac.rs.MailApi.entity.Tag;
import uns.ac.rs.MailApi.entity.User;
import uns.ac.rs.MailApi.service.UserService;

import java.util.ArrayList;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRegister userDTO) /*throws AuthenticationException, IOException */ {

        if(userDTO.getFirstName() == null) {
            return new ResponseEntity<String>("First name can't be empty",HttpStatus.BAD_REQUEST);
        }else if(userDTO.getLastName() == null) {
            return new ResponseEntity<String>("Last name can't be empty",HttpStatus.BAD_REQUEST);
        }else if(userDTO.getUsername() == null) {
            return new ResponseEntity<String>("Username can't be empty",HttpStatus.BAD_REQUEST);
        }else if(userDTO.getPassword() == null) {
            return new ResponseEntity<String>("Password can't be empty",HttpStatus.BAD_REQUEST);
        }

        // -- Check check that username already exists
        User usernameTaken = userService.findByUsername(userDTO.getUsername());
        if (usernameTaken != null) {
            return new ResponseEntity<String>("Username already exists",HttpStatus.BAD_REQUEST);
        }


        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setAccounts(new ArrayList<Account>());
        user.setContacts(new ArrayList<Contact>());
        user.setTags(new ArrayList<Tag>());

        // ----- Saving new user to database -----
        user = userService.save(user);

        return new ResponseEntity<String>("Success",HttpStatus.OK);
    }
}

