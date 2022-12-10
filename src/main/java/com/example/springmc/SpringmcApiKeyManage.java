package com.example.springmc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SpringmcApiKeyManage {
	
    @Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String getApiKey(){
		
		String query = "SELECT apiKey FROM deepl LIMIT 1";
		Map<String, Object> apiKey = jdbcTemplate.queryForMap(query);
		return (String) apiKey.get("apiKey");
	}

	public Boolean checkRegisteredApiKey(){
		
		String query = "SELECT case when count(*) = 0 then '0' else '1' end count FROM deepl";
		Map<String, Object> apiKeyCount = jdbcTemplate.queryForMap(query);
		return !apiKeyCount.get("count").equals("0");
	}

	public Boolean addRegisterApiKey(String targetApiKey){
		
		String query = "INSERT INTO deepl (apiKey) VALUES (?)";
		return (jdbcTemplate.update(query, targetApiKey) != 0);
	}
}
