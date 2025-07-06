package com.project.journalApp.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("config")
@Data
public class Cache {
	@Id
	private ObjectId id;
	private String key;
	private String value;
}
