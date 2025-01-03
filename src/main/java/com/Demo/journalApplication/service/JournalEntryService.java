package com.Demo.journalApplication.service;


import com.Demo.journalApplication.entitiy.JournalEntry;
import com.Demo.journalApplication.entitiy.UserEntity;
import com.Demo.journalApplication.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;
    Logger log;

    @Transactional
    public void saveEntry(JournalEntry journalEntry,String userName){

            UserEntity user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournals().add(saved);
            user.setDate(LocalDateTime.now());
            userService.saveUser(user);

    }

    public void saveEntry(JournalEntry journalEntry){
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryRepository.save(journalEntry);
        }
        catch (Exception e){

            log.error("Exception : ",e);
        }
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public JournalEntry findById(ObjectId id){
        return journalEntryRepository.findById(id).orElse(null);
    }
    public void deleteById(ObjectId id,String userName){
        UserEntity user = userService.findByUserName(userName);
        user.getJournals().removeIf(x -> x.getId().equals(id));
        user.setDate(LocalDateTime.now());
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }
}
