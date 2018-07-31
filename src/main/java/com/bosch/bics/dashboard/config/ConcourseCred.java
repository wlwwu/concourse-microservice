package com.bosch.bics.dashboard.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:concourse.properties")
@ConfigurationProperties
public class ConcourseCred {
	private String concourse_host;
	private String concourse_username;
	private String concourse_password;
    private String concourse_token_url;
    private String concourse_count_url;
    
	
	public String getconcourse_host() {
		return concourse_host;
	}
	public void setconcourse_host(String concourse_host) {
		this.concourse_host = concourse_host;
	}
	public String getconcourse_username() {
		return concourse_username;
	}
	public void setHttpProxyPort(String concourse_username) {
		this.concourse_username = concourse_username;
	}
	public String getconcourse_password() {
		return concourse_password;
	}
	public void setconcourse_password(String concourse_password) {
		this.concourse_password = concourse_password;
	}
	public String getconcourse_token_url() {
		return concourse_token_url;
	}
	public void setconcourse_token_url(String concourse_token_url) {
		this.concourse_token_url = concourse_token_url;
	}
	public String getconcourse_count_url() {
		return concourse_count_url;
	}
	public void setconcourse_count_url(String concourse_count_url) {
		this.concourse_count_url = concourse_count_url;
	}
	
}