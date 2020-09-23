package uns.ac.rs.MailApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.MailApi.dto.FolderDTO;
import uns.ac.rs.MailApi.entity.Account;
import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Message;
import uns.ac.rs.MailApi.entity.Rule;
import uns.ac.rs.MailApi.service.AccountService;
import uns.ac.rs.MailApi.service.FolderService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("acc/{index}/folders")
public class FolderController {

    @Autowired
    private FolderService folderService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getAllFolders(
            @PathVariable("index") int accountIndex,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.BAD_REQUEST);

        // ----- Obtaining data from database -----
        //List<Folder> folders = folderService.findAll(account);
        List<Folder> folders = folderService.findRootFolders(account);

        // ----- Preparing data for front end -----
        List<FolderDTO> foldersDTO = new ArrayList<FolderDTO>();
        for (Folder folder : folders) {
            FolderDTO folderDTO = new FolderDTO(folder);
            foldersDTO.add(folderDTO);
        }

        return new ResponseEntity<List<FolderDTO>>(foldersDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFolder(
            @PathVariable("index") int accountIndex,
            @PathVariable("id") int folderId,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        else if (folderId < 1)
            return new ResponseEntity<>("You must provide an ID for a folder!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.NOT_FOUND);

        // ----- Obtaining data from database -----
        Folder folder = folderService.findOne(folderId, account);
        if (folder == null)
            return new ResponseEntity<>("Unexisting folder.", HttpStatus.NOT_FOUND);

        // ----- Preparing data for front end -----
        FolderDTO folderDTO = new FolderDTO(folder);

        return new ResponseEntity<FolderDTO>(folderDTO, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> createFolder(
            @PathVariable("index") int accountIndex,
            @RequestBody Folder folder,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.BAD_REQUEST);

        // ----- Setting initial folder data -----
        folder.setAccount(account);
        folder.setMessages(new ArrayList<Message>());

        if (folder.getSubFolders() == null || folder.getSubFolders().isEmpty()) {
            folder.setSubFolders(new ArrayList<Folder>());
        } else {
            for (Folder subFolder : folder.getSubFolders()) {
                subFolder.setAccount(account);
                subFolder.setParentFolder(folder);

                if (subFolder.getRules() == null || subFolder.getRules().isEmpty()) {
                    subFolder.setRules(new ArrayList<Rule>());
                } else {
                    subFolder.fixRules();
                }
            }
        }

        if (folder.getRules() == null || folder.getRules().isEmpty()) {
            folder.setRules(new ArrayList<Rule>());
        } else {
            folder.fixRules();
        }

        // ----- Saving to database -----
        folder = folderService.save(folder);

        return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.OK);
    }

    // KAKO DA NAPRAVIM DA SE VRSI PROVERA NALOGA NA NIVOU SQL-A?
    @PutMapping
    public ResponseEntity<?> updateFolder(
            @PathVariable("index") int accountIndex,
            @RequestBody FolderDTO folderDTO,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        else if (folderDTO.getId() == null)
            return new ResponseEntity<>("You must provide an ID for a folder you wish to update!", HttpStatus.BAD_REQUEST);
        else if (folderDTO.getName().equals("Inbox") || folderDTO.getName().equals("Outbox") || folderDTO.getName().equals("Drafts"))
            return new ResponseEntity<String>("You can't create root folders!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.BAD_REQUEST);

        // ----- Obtaining data from database -----
        Folder folder = folderService.findOne(folderDTO.getId(), account);
        if (folder == null)
            return new ResponseEntity<String>("Unexisting folder.", HttpStatus.NOT_FOUND);
        else if (folder.getName().equals("Inbox") || folder.getName().equals("Outbox") || folder.getName().equals("Drafts"))
            return new ResponseEntity<String>("You can't edit root folders!", HttpStatus.BAD_REQUEST);

        // ----- Setting data from front end -----
        folder.setName(folderDTO.getName());

        // ----- Saving to database -----
        folder = folderService.save(folder);

        return new ResponseEntity<FolderDTO>(new FolderDTO(folder), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFolder(
            @PathVariable("index") int accountIndex,
            @PathVariable("id") int folderId,
            Principal principal)
    {
        // ----- Request data validation -----
        if (accountIndex < 0)
            return new ResponseEntity<>("You must provide an ID for an account!", HttpStatus.BAD_REQUEST);
        else if (folderId == 0)
            return new ResponseEntity<>("You must provide an ID for a folder you wish to delete!", HttpStatus.BAD_REQUEST);

        // ----- Resolving current user's account -----
        Account account = accountService.resolveAccount(principal, accountIndex);
        if (account == null)
            return new ResponseEntity<>("Unexisting account.", HttpStatus.BAD_REQUEST);

        // ----- Obtaining data from database -----
        Folder folder = folderService.findOne(folderId, account);
        if (folder == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if (folder.getName().equals("Inbox") || folder.getName().equals("Outbox") || folder.getName().equals("Drafts"))
            return new ResponseEntity<String>("You can't delete root folders!", HttpStatus.BAD_REQUEST);

        // ----- Removing from database -----
        folderService.remove(folderId);

        return new ResponseEntity<String>("Successfully removed!", HttpStatus.OK);
    }

}

