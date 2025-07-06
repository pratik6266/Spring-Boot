package com.project.journalApp.cache;

import com.project.journalApp.entity.Cache;
import com.project.journalApp.repository.CacheRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

	@Autowired
	private CacheRepository cacheRepository;

	public Map<String, String> APP_CACHE = new HashMap<>();

	@PostConstruct
	public void init(){
		List<Cache> all = cacheRepository.findAll();
		for(Cache cache: all){
			APP_CACHE.put(cache.getKey(), cache.getValue());
		}
	}
}
