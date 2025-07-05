package com.project.journalApp.controller;

import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import com.project.journalApp.service.JournalEntryService;
import com.project.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryController.class);

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<JournalEntry> data = user.getJournalEntries();
            if(data == null || data.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<JournalEntry>>(data, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("Error fetching journal entries: ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            if(username == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User user = userService.getUserByUsername(username);
            if(user == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (myEntry == null || myEntry.getTitle() == null || myEntry.getContent() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            myEntry.setDate(new java.util.Date());
            journalEntryService.saveEntry(myEntry, user);
            return new ResponseEntity<JournalEntry>(myEntry, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Boolean> getEntryById(@PathVariable ObjectId id){
        Optional<JournalEntry> entry = journalEntryService.findById(id);
        if(entry.isEmpty()){
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Boolean> deleteEntryById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean b = journalEntryService.deleteById(id, username);
        if(!b){
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){
        JournalEntry local = journalEntryService.findById(myId).orElse(null);
        if(local == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        local.setTitle(myEntry.getTitle());
        local.setContent(myEntry.getContent());
        journalEntryService.saveEntry2(local);
        return new ResponseEntity<JournalEntry>(local, HttpStatus.OK);
    }
}
