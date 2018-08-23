package com.bosch.bics.dashboard.bicsdashboardservices.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConcourseBuild {
    private String id;
    @JsonProperty("team_name") 
    private String teamName;
    private String name;
    private String status;
    @JsonProperty("job_name")
    private String jobName;
    @JsonProperty("api_url")
    private String apiUrl;
    @JsonProperty("pipeline_name")
    private String pipelineName;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("end_time")
    private String endTime;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getJobName() {
        return jobName;
    }
    
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getApiUrl() {
        return apiUrl;
    }
    
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
    
    public String getPipelineName() {
        return pipelineName;
    }
    
    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }
    
    public String getStartTime() {
        return startTime;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public String getEndTime() {
        return endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
