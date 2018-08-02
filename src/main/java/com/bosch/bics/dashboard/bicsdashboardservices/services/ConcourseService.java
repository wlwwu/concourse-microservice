package com.bosch.bics.dashboard.bicsdashboardservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bosch.bics.dashboard.bicsdashboardservices.config.ConcourseCred;

@Controller
public class ConcourseService {
	
    @Autowired
    private ConcourseCred concourseParam;
    
	private String concoursePassword;
	private String concourseCounturl;
	private String concourseUsername;
	private String concourseHost;
	private String concourseTokenurl;
    
	public String getConcourseUsername() {
		concourseUsername = concourseParam.getConcourseUsername();
		return concourseUsername;
	}

	public String getConcoursePassword() {
		concoursePassword = concourseParam.getConcoursePassword();
		return concoursePassword;
	}

	public String getConcourseCounturl() {
		concourseCounturl = concourseParam.getConcourseCounturl();
		return concourseCounturl;
	}

	public String getConcourseHost() {
		concourseHost = concourseParam.getConcourseHost();
		return concourseHost;
	}

	public String getConcourseTokenurl() {
		concourseTokenurl = concourseParam.getConcourseTokenurl();
		return concourseTokenurl;
	}
	
}
