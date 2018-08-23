package com.bosch.bics.dashboard.bicsdashboardservices.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:concourse.properties")
@ConfigurationProperties
public class ConcourseCred {
    private String concoursePassword;
    private String concourseCounturl;
    private String concourseUsername;
    private String concourseHost;
    private String concourseTokenurl;

    public String getConcourseTokenurl() {
        return concourseTokenurl;
    }
    
    public void setConcourseTokenurl(String concourseTokenurl) {
        this.concourseTokenurl = concourseTokenurl;
    }
    
    public String getConcourseCounturl() {
        return concourseCounturl;
    }
    
    public void setConcourseCounturl(String concourseCounturl) {
        this.concourseCounturl = concourseCounturl;
    }
    
    public String getConcoursePassword() {
        return concoursePassword;
    }
    
    public void setConcoursePassword(String concoursePassword) {
        this.concoursePassword = concoursePassword;
    }
    
    public String getConcourseHost() {
        return concourseHost;
    }
    
    public void setConcourseHost(String concourseHost) {
        this.concourseHost = concourseHost;
    }
    
    public String getConcourseUsername() {
        return concourseUsername;
    }
    
    public void setConcourseUsername(String concourseUsername) {
        this.concourseUsername = concourseUsername;
    }



}
