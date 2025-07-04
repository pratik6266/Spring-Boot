package com.project.journalApp.controller;

import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import com.project.journalApp.service.JournalEntryService;
import com.project.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("{userId}")
    public ResponseEntity<List<JournalEntry>> getAll(@PathVariable ObjectId userId){
        User user = userService.getUser(userId);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<JournalEntry> data = user.getJournalEntries();
        if(data == null || data.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<JournalEntry>>(data, HttpStatus.OK);
    }

    @PostMapping("{userId}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,
                                                    @PathVariable ObjectId userId){
        try {
            if(userId == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User user = userService.getUser(userId);
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

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> findEntry(@PathVariable ObjectId myId){
        Optional<JournalEntry> data = journalEntryService.findById(myId);
        if(data.isPresent()){
            return new ResponseEntity<JournalEntry>(data.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{username}/{id}")
    public ResponseEntity<Boolean> deleteEntryById(@PathVariable ObjectId id, @PathVariable String username){
        boolean b = journalEntryService.deleteById(id, username);
        if(!b){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(b, HttpStatus.OK);
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
