package com.project.journalApp.service;

import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import com.project.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, User user){
        JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
        User userFromDb = userService.getUserByUsername(user.getUsername());
        userFromDb.getJournalEntries().add(savedEntry);
        userService.saveExistingUser(userFromDb);
    }

    public void saveEntry2(JournalEntry journalEntry){
        JournalEntry save = journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public boolean deleteById(ObjectId id, String username) {
        if(id == null || username == null) {
            return false;
        }
        Optional<JournalEntry> entry = journalEntryRepository.findById(id);
        if(entry.isPresent()){
            journalEntryRepository.deleteById(id);
            User user = userService.getUserByUsername(username);
            if(user != null) {
                user.getJournalEntries().remove(entry.get());
                userService.saveExistingUser(user);
                return true;
            }
            else return false;
        }
        return false;
    }

}
