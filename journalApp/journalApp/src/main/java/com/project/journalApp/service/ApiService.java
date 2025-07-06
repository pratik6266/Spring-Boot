package com.project.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiService {

	private static final String api = "https://soloroom-server.onrender.com";

	@Autowired
	private RestTemplate restTemplate;

	public String getApi() {
		ResponseEntity<String> response = restTemplate.exchange(api, HttpMethod.GET, null, String.class);
		return response.getBody();
	}
}

