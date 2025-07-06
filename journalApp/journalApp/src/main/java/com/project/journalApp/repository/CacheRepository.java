package com.project.journalApp.repository;

import com.project.journalApp.entity.Cache;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CacheRepository extends MongoRepository<Cache, ObjectId> {
}
