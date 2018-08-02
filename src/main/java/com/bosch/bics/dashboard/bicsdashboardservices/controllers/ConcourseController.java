package com.bosch.bics.dashboard.bicsdashboardservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.bosch.bics.dashboard.bicsdashboardservices.services.ConcourseService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.Base64;

@RestController
public class ConcourseController {
	
	@Autowired
	private ConcourseService concourseService;
	private String concoursePassword;
	private String concourseCounturl;
	private String concourseUsername;
	private String concourseHost;
	private String concourseTokenurl;
	private String concourseRestpoint;
	private String stringauth;
	private byte[] encodePassword;
    private String authHeader;
	private String token;
	
	public String getToken() throws IOException{
        concourseUsername = concourseService.getConcourseUsername();
        concoursePassword = concourseService.getConcoursePassword();
        concourseHost = concourseService.getConcourseHost();
        concourseTokenurl = concourseService.getConcourseTokenurl();
        concourseCounturl = concourseService.getConcourseCounturl();
        concourseRestpoint = "http://" + concourseHost + concourseTokenurl ;
    
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        
        stringauth = concourseUsername+':'+concoursePassword;
        encodePassword = Base64.getEncoder().encode(stringauth.getBytes());
        authHeader = "Basic "+ encodePassword;
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =  restTemplate.exchange(concourseRestpoint,HttpMethod.GET,entity,String.class);
        String tokenJson = response.getBody().toString();
        token = extractJsonToken(tokenJson);
	return token;	
	}
	
	public String extractJsonToken(String tokenJson) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode fileNode = objectMapper.readTree(tokenJson);
		String token = fileNode.path("value").asText();
		return token;
		
	}
	
	public List<String> getBuild() throws IOException{
		
        concourseHost = concourseService.getConcourseHost();
        concourseCounturl = concourseService.getConcourseCounturl();
        concourseRestpoint = "http://" + concourseHost + concourseCounturl;
        
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        token = getToken();
        authHeader= "Bearer " + token; 
        headers.set("ATC-Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response =  restTemplate.exchange(concourseRestpoint,HttpMethod.GET,entity,List.class);
        List<String> buildList =  (List) response.getBody();
		return buildList;	
	}
	
	public Map<String, Integer> countBuild(List<String> buildList) throws IOException{
		Map<String, Integer> countmap = new HashMap<String, Integer>();
		List<String> buildL = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < buildList.size(); i++) {
			JsonNode actualObj = mapper.readTree(buildList.get(i));
		    String jobName = actualObj.path("job_name").asText();
		    if (buildL.contains(jobName)){
		    	Integer count = countmap.get(jobName) + 1;
		    	countmap.put(jobName, count);
		    }else {
		    	buildL.add(jobName);
		    	countmap.put(jobName, 1);
		    }   
		}
		return countmap;
	}
	
	
	
	
	
	@RequestMapping("/v1/buildcount")
	public Map<String, Integer> buildcount() throws IOException {
		 List<String> buildList = getBuild();
         Map<String, Integer> countMap = countBuild(buildList);
          return countMap;
	}
}
