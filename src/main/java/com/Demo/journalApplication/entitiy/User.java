package com.Demo.journalApplication.entitiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private ObjectId id;
	@Indexed(unique = true)
	@NonNull
	private String userName;
	@NonNull
	private String password;
	@DBRef
	private List<JournalEntry> journals = new ArrayList<>();

	private LocalDateTime date;

	private List<String> roles = new ArrayList<>();
}
