package com.project.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void testSendMail() {
		redisTemplate.opsForValue().set("email", "pratikraj220011@gmail.com");
		String email = redisTemplate.opsForValue().get("email");
		System.out.println("Fetched email: " + email);
	}
}
