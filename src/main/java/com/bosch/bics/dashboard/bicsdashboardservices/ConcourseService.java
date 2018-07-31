package com.bosch.bics.dashboard.bicsdashboardservices;

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
import com.bosch.bics.dashboard.api_resource.ConcourseEndpoint;



@RestController
public class ConcourseService {

	@RequestMapping("/token")
	public String sayHello() {
		ConcourseEndpoint conendponit = new ConcourseEndpoint();
		String result = conendponit.get_token();
		return "Hello,World!"+ result ;
	}
}