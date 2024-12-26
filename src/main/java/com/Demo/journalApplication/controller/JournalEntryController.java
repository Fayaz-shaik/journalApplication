package com.Demo.journalApplication.controller;

import com.Demo.journalApplication.entitiy.JournalEntry;
import com.Demo.journalApplication.entitiy.UserEntity;
import com.Demo.journalApplication.service.JournalEntryService;
import com.Demo.journalApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

//    private final Map<Long, JournalEntry> journalEntries = new HashMap<>();
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllJournalsOfUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntity user = userService.findByUserName(userName);
        if(user==null){
            return new ResponseEntity<>("User is not Found. Provide proper user",HttpStatus.BAD_REQUEST);
        }
        List<JournalEntry> all = user.getJournals();
        if(all.isEmpty())
            return new ResponseEntity<>("User is found but User don't have any Journals Saved.",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println("Exception logs : "+e);
            return new ResponseEntity<>("Some Error Occured in Creating Journal Entry",HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryByID(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        List<JournalEntry> journalsByUser = userService.findJournalsByUser(userName);

        List<JournalEntry> journalEntry = journalsByUser.stream().filter(x -> x.getId().equals(myId)).toList();
		return !journalEntry.isEmpty()
				? new ResponseEntity<>(journalEntry.getFirst(), HttpStatus.OK)
				: new ResponseEntity<>("Respective ID is not associated with User",HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryByID(@PathVariable ObjectId myId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        List<JournalEntry> journalsByUser = userService.findJournalsByUser(userName);

        List<JournalEntry> journalEntry = journalsByUser.stream().filter(x -> x.getId().equals(myId)).toList();

		if (!journalEntry.isEmpty()) {
			journalEntryService.deleteById(myId, userName);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("Respective Journal is not associated with the User. Journal ID : "+myId,HttpStatus.NOT_FOUND);
		}
	}

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        UserEntity user = userService.findByUserName(userName);
        List<JournalEntry> journalsByUser = userService.findJournalsByUser(userName);

        List<JournalEntry> journalEntry = journalsByUser.stream().filter(x -> x.getId().equals(myId)).toList();

		if (journalEntry.isEmpty()) {
			return new ResponseEntity<>("No Journal Entry Found with respective Journal ID : "+myId,HttpStatus.NOT_FOUND);
		}
        JournalEntry old = journalEntry.getFirst();
		old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().isEmpty() ? myEntry.getTitle() : old.getTitle());
		old.setContent(myEntry.getContent() != null && !myEntry.getContent().isEmpty() ? myEntry.getContent() : old.getContent());
		journalEntryService.saveEntry(old);
		user.setDate(LocalDateTime.now());
		userService.saveUser(user);
		return new ResponseEntity<>(old, HttpStatus.OK);
	}
}
