package com.bosch.bics.dashboard.api_resource;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import com.bosch.bics.dashboard.config.ConcourseCred;


public class ConcourseEndpoint {
    @Autowired
    private ConcourseCred Concourse_param;
    private static final Logger logger = LoggerFactory.getLogger(ConcourseEndpoint.class);
    private String concourse_host;
    private String concourse_username;
    private String concourse_password;
    private String concourse_token_url;
    private String concourse_count_url;
    @Autowired
    private RestTemplateBuilder restbuilder;
    
	public String get_token(){
    RestTemplate restTemplate = restbuilder.basicAuthorization(concourse_username,concourse_password).build();
    ResponseBean  result = restTemplate.getForEntity("http://10.187.215.108:8080/api/v1/teams/main/auth/token",ResponseBean.class );
	System.out.println(result);	
	return result;
			
	}
}