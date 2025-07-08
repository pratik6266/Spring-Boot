package com.project.journalApp.repository;

import com.project.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

	@Autowired
	private MongoTemplate mongoTemplate;

	public List<User> getUserForSA(){
		Query query = new Query();
		Criteria criteria = new Criteria();
		query.addCriteria(criteria.andOperator(Criteria.where("email").exists(true),
						Criteria.where("sentimentAnalysis").is("true")));
		List<User> users = mongoTemplate.find(query, User.class);
		return users;
	}

}
