package com.project.journalApp.Scheduler;

import com.project.journalApp.entity.JournalEntry;
import com.project.journalApp.entity.User;
import com.project.journalApp.repository.UserRepositoryImpl;
import com.project.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailSchedular {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserRepositoryImpl usesrRepositoryImpl;


//	@Scheduled(cron = "0 0 9 * * SUN")
	public void fetchUsersAndSendEmail(){
		List<User> users = usesrRepositoryImpl.getUserForSA();
		for(User user: users){
			emailService.sendEmail(user.getEmail(), "Subject", "This is a test email for sentiment analysis.");
		}
	}

}
