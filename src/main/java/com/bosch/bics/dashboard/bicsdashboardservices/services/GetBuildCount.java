package com.bosch.bics.dashboard.bicsdashboardservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bosch.bics.dashboard.bicsdashboardservices.models.ConcourseBuild;
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
import java.nio.charset.Charset;
import java.util.Base64;

@Service
public class GetBuildCount {
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
        concourseRestpoint = "http://" + concourseHost + concourseTokenurl ;
        System.out.println("------->"+concourseRestpoint);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createAuthorizationHeader(concourseUsername,concoursePassword);
 
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response =  restTemplate.exchange(concourseRestpoint,HttpMethod.GET,entity,String.class);
        String tokenJson = response.getBody().toString();
        System.out.println(tokenJson);
        token = extractJsonToken(tokenJson);
	return token;	
	}
	
	public String extractJsonToken(String tokenJson) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode fileNode = objectMapper.readTree(tokenJson);
		String token = fileNode.path("value").asText();
		System.out.println("------------->token: " + token);
		return token;
		
	}
	
	public List<ConcourseBuild> getBuild() throws IOException{
		
        concourseHost = concourseService.getConcourseHost();
        concourseCounturl = concourseService.getConcourseCounturl();
        System.out.println("====counturl===" + concourseCounturl);
        String concourseBuildsUrl = "http://" + concourseHost + concourseCounturl;
        
		RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        token = getToken();
//        HttpHeaders headers = createAuthorizationHeader(concourseUsername,concoursePassword);
        authHeader= "ATC-Authorization=Bearer " + token; 
        System.out.println("------------->Cookie:" + authHeader);
        System.out.println("-------------->restpoint"+concourseBuildsUrl );
        headers.add("Cookie", authHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<ConcourseBuild>> response =  restTemplate.exchange(concourseBuildsUrl,HttpMethod.GET,entity,new ParameterizedTypeReference<List<ConcourseBuild>>() {} );
        List<ConcourseBuild> buildList =  (List<ConcourseBuild>) response.getBody();
        for(int i = 0; i<buildList.size(); i++){
        	System.out.println("-->"+buildList.get(i).getJobName());
        }
        
		return buildList;	
	}
	
	public Map<String, Integer> countBuild(List<ConcourseBuild> buildList) throws IOException{
		Map<String, Integer> countmap = new HashMap<String, Integer>();
		List<String> buildL = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
		for (int i = 0; i < buildList.size(); i++) {
//			JsonNode actualObj = mapper.readTree(buildList.get(i));
//		    String jobName = actualObj.path("job_name").asText();
			String jobName = buildList.get(i).getJobName();
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
	private HttpHeaders createAuthorizationHeader(String username, String password){
		   return new HttpHeaders() {
			private static final long serialVersionUID = 3804439706099215627L;
			{
		         String auth = username + ":" + password;
		         byte[] encodedAuth = Base64.getEncoder().encode( 
		            auth.getBytes(Charset.forName("US-ASCII")) );
		         String authHeader = "Basic " + new String( encodedAuth );
		         set( "Authorization", authHeader );
		      }};
		}
}
