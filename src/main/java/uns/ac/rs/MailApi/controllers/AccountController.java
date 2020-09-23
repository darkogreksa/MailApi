package uns.ac.rs.MailApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.MailApi.dto.AccountDTO;
import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.User;
import uns.ac.rs.MailApi.service.AccountService;
import uns.ac.rs.MailApi.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/acc")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<?> getAllAccounts(Principal principal)
    {
        // ----- Resolving current user -----
        User user = userService.findByUsername(principal.getName());

        // ----- Obtaining data from database -----
        List<Account> accounts = accountService.findByUser(user);
        if (accounts == null)
            return new ResponseEntity<String>("This user has no accounts registered!", HttpStatus.NOT_FOUND);

        // ----- Preparing data for front end -----
        List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>();
        for (Account account : accounts) {
            accountsDTO.add(new AccountDTO(account));
        }

        return new ResponseEntity<List<AccountDTO>>(accountsDTO, HttpStatus.OK);

    }

    @CrossOrigin
    @GetMapping("/{accountIndex}")
    public ResponseEntity<?> getAccount(
            @PathVariable("accountIndex") Integer index,
            Principal principal)
    {
        // ----- Resolving current user -----
        User user = userService.findByUsername(principal.getName());

        // ----- Obtaining data from database -----
        List<Account> accounts = accountService.findByUser(user);
        if (accounts == null)
            return new ResponseEntity<String>("This user has no accounts registered!", HttpStatus.NOT_FOUND);
        else if (accounts.size() <= index)
            return new ResponseEntity<String>("Unexisting account!", HttpStatus.NOT_FOUND);

        // ----- Preparing data for front end -----
        AccountDTO accountDTO = new AccountDTO(accounts.get(index));

        return new ResponseEntity<AccountDTO>(accountDTO, HttpStatus.OK);

    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<?> createAccount(
            @RequestBody AccountDTO accountDTO,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountDTO.getSmtpAddress().isEmpty())
            return new ResponseEntity<String>("Smtp Address can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getSmtpPort() == 0)
            return new ResponseEntity<String>("Smtp Port can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getInServerType() == 0)
            return new ResponseEntity<String>("In Server Type can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getInServerAddress().isEmpty())
            return new ResponseEntity<String>("In Server Address can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getInServerPort() == 0)
            return new ResponseEntity<String>("In Server Port can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getUsername().isEmpty())
            return new ResponseEntity<String>("Username can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getPassword().isEmpty())
            return new ResponseEntity<String>("Password can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getDisplayName().isEmpty())
            return new ResponseEntity<String>("Display Name can't be empty!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user -----
        User user = userService.findByUsername(principal.getName());

        // ----- Setting data from frontend -----
        Account account = new Account();
        account.setSmtpAddress(accountDTO.getSmtpAddress());
        account.setSmtpPort(accountDTO.getSmtpPort());
        account.setInServerType(accountDTO.getInServerType());
        account.setInServerAddress(accountDTO.getInServerAddress());
        account.setInServerPort(accountDTO.getInServerPort());
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setDisplayName(accountDTO.getDisplayName());
        // ----- Setting data from server -----
        account.setUser(user);
        account.setFolders(createInitialFolders(account));

        // ----- Saving to database -----
        accountService.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<?> updateAccount(
            @RequestBody AccountDTO accountDTO,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountDTO.getSmtpAddress().isEmpty())
            return new ResponseEntity<String>("Smtp Address can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getSmtpPort() == 0)
            return new ResponseEntity<String>("Smtp Port can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getInServerType() == 0)
            return new ResponseEntity<String>("In Server Type can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getInServerAddress().isEmpty())
            return new ResponseEntity<String>("In Server Address can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getInServerPort() == 0)
            return new ResponseEntity<String>("In Server Port can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getUsername().isEmpty())
            return new ResponseEntity<String>("Username can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getPassword().isEmpty())
            return new ResponseEntity<String>("Password can't be empty!", HttpStatus.BAD_REQUEST);
        else if (accountDTO.getDisplayName().isEmpty())
            return new ResponseEntity<String>("Display Name can't be empty!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user -----
        User user = userService.findByUsername(principal.getName());

        // ----- Obtaining data from database -----
        Account account = accountService.findByIdAndUser(accountDTO.getId(), user);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Setting data from frontend -----
        account.setSmtpAddress(accountDTO.getSmtpAddress());
        account.setSmtpPort(accountDTO.getSmtpPort());
        account.setInServerType(accountDTO.getInServerType());
        account.setInServerAddress(accountDTO.getInServerAddress());
        account.setInServerPort(accountDTO.getInServerPort());
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setDisplayName(accountDTO.getDisplayName());

        // ----- Saving to database -----
        accountService.save(account);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping
    public ResponseEntity<?> deleteAccount(
            @RequestBody AccountDTO accountDTO,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountDTO.getId() == 0)
            return new ResponseEntity<String>("You must provide an ID for an account you wish to delete!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user -----
        User user = userService.findByUsername(principal.getName());

        // ----- Obtaining data from database -----
        Account account = accountService.findByIdAndUser(accountDTO.getId(), user);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Removing from database -----
        accountService.remove(accountDTO.getId());

        return new ResponseEntity<String>("Successfully removed!", HttpStatus.OK);
    }

    // ----- HELPER METHODS -----

    /**
     * Creates initial, empty folders that every account is
     * required to have.
     *
     * @param Account entity - chocmailbackend.entity.Account.
     * @return Empty folders that you can place in a new account.
     */
    private ArrayList<Folder> createInitialFolders(Account account) {
        ArrayList<Folder> folders = new ArrayList<Folder>();

        Folder inbox = new Folder();

        inbox.setName("Inbox");
        inbox.setAccount(account);

        folders.add(inbox);

        Folder drafts = new Folder();

        drafts.setName("Drafts");
        drafts.setAccount(account);

        folders.add(drafts);

        Folder outbox = new Folder();

        outbox.setName("Outbox");
        outbox.setAccount(account);

        folders.add(outbox);

        // TODO: Dodaj neka pravila za ove foldere!

        return folders;
    }

}

