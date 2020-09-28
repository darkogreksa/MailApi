package uns.ac.rs.MailApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.MailApi.dto.UserDTO;
import uns.ac.rs.MailApi.entity.*;
import uns.ac.rs.MailApi.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;



    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<UserDTO> users = new ArrayList<UserDTO>();

        for (User user : userService.findAll()) {
            users.add(new UserDTO(user));
        }

        return users;
    }

    @GetMapping("/debug")
    public List<User> getAllRealUsers() {
        return userService.findAll();
    }


    @PostMapping("/registration")
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {


        // ----- Request data validation -----
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
        // ----- Setting data from frontend -----
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // ----- Setting data from server -----
        user.setAccounts(new ArrayList<Account>());
        user.setContacts(new ArrayList<Contact>());
        user.setTags(new ArrayList<Tag>());

        Authority authority = new Authority();
        authority.setId(new Long(1));
        authority.setName("USER");

        user.addAuthority(authority);

        // ----- Saving new user to database -----
        user = userService.save(user);

        return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.CREATED);


    }

    @PutMapping("/editProfile")
    public ResponseEntity<?> editProfile(@RequestBody UserDTO user,Principal principal) {

        // ----- Request data validation -----
        if(user.getFirstName() == null) {
            return new ResponseEntity<String>("First name can't be empty",HttpStatus.BAD_REQUEST);
        }else if(user.getLastName() == null) {
            return new ResponseEntity<String>("Last name can't be empty",HttpStatus.BAD_REQUEST);
        }


        User existingUser = userService.findByUsername(principal.getName());

        // ----- Setting data from frontend -----
        if(existingUser != null) {

            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());

            existingUser = userService.save(existingUser);

            return new ResponseEntity<>(HttpStatus.OK);

        }else {
            return new ResponseEntity<>("User with this username doesn't exists", HttpStatus.UNAUTHORIZED);
        }


    }


    @PutMapping("/editPassword")
    public ResponseEntity<?> editPassword(@RequestBody UserDTO user,Principal principal) {


        if(user.getUsername() == null) {
            return new ResponseEntity<String>("Username can't be empty",HttpStatus.BAD_REQUEST);
        }else if(user.getPassword() == null) {
            return new ResponseEntity<String>("Password can't be empty",HttpStatus.BAD_REQUEST);
        }

        User existingUser = userService.findByUsername(user.getUsername());

        // ----- Setting data from frontend -----
        if(existingUser != null)
        {

            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            existingUser = userService.save(existingUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>("User with this username doesn't exists.", HttpStatus.UNAUTHORIZED);
        }
    }
}

