package com.project.journalApp.service;

import com.project.journalApp.cache.AppCache;
import com.project.journalApp.entity.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
// work same as @Component but used for service layer
public class ApiService {

//	@Value("${your_name}")
//	private final String name;

	private static final String api = "https://soloroom-server.onrender.com";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AppCache appCache;

	public String getApi() {
		/*
		* This is how you send payload to the api
		* HttpHeaders httpHeaders = new HttpHeaders();
		* httpHeaders.set("key", "value");
		* User user = User.builder().name("Raj").password("123").build();
		* HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
		* Send anything just make it httpHeader using respective function and then give it to httpEntity.
		* */
		String api = appCache.APP_CACHE.get("api");
		ResponseEntity<String> response = restTemplate.exchange(api, HttpMethod.GET, null, String.class);
		return response.getBody();
	}
}

