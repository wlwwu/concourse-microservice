package com.bosch.bics.dashboard.bicsdashboardservices.services;

import com.bosch.bics.dashboard.bicsdashboardservices.controllers.ConcourseController;
import com.bosch.bics.dashboard.bicsdashboardservices.models.ConcourseBuild;
import com.bosch.bics.dashboard.bicsdashboardservices.services.ConcourseService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private String authHeader;
    private String token;

    private static final Logger logger = LoggerFactory.getLogger(ConcourseController.class);
      
    /**
     * Init concourse parameters.
     */
    public void init() {
        logger.info("Count Service parameter init starts here!");
        // concourseUsername = System.getenv("concourseUsername");
        // concoursePassword = System.getenv("concoursePassword");
        // concourseHost = System.getenv("concourseHost");
        // concourseCounturl = System.getenv("concourseCounturl");
        // concourseTokenurl = System.getenv("concourseTokenurl");
        //
        concourseUsername = concourseService.getConcourseUsername();
        concoursePassword = concourseService.getConcoursePassword();
        concourseHost = concourseService.getConcourseHost();
        concourseTokenurl = concourseService.getConcourseTokenurl();
        concourseCounturl = concourseService.getConcourseCounturl();
        logger.info("Count Service parameter init ends!");

    }

    /**
     * Get token of concourse.
     */
    public String getToken() throws IOException {
        init();
        concourseRestpoint = "http://" + concourseHost + concourseTokenurl;
        logger.info("concourseRestpoint displayed here: " + concourseRestpoint);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createAuthorizationHeader(concourseUsername, concoursePassword);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(concourseRestpoint,
                HttpMethod.GET, entity, String.class);
        String tokenJson = response.getBody();
        token = extractJsonToken(tokenJson);
        return token;
    }

    /**
     * Extract token.
     */
    public String extractJsonToken(String tokenJson) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode fileNode = objectMapper.readTree(tokenJson);
        String token = fileNode.path("value").asText();
        return token;

    }

    /**
     * Get build list information of concourse.
     */
    public List<ConcourseBuild> getBuild() throws IOException {
        init();
        String concourseBuildsUrl = "http://" + concourseHost + concourseCounturl;
        logger.info("concourseBuildsUrl displayed here: " + concourseBuildsUrl);
        HttpHeaders headers = new HttpHeaders();
        token = getToken();
        authHeader = "ATC-Authorization=Bearer " + token;
        headers.add("Cookie", authHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<ConcourseBuild>> response = restTemplate.exchange(concourseBuildsUrl,
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<ConcourseBuild>>() {});
        List<ConcourseBuild> buildList = (List<ConcourseBuild>) response.getBody();
        return buildList;
    }

    /**
     * Count total build count.
     */
    public Map<String, Integer> countBuild(List<ConcourseBuild> buildList) throws IOException {
        Map<String, Integer> countmap = new HashMap<String, Integer>();
        List<String> buildL = new ArrayList<String>();
        for (int i = 0; i < buildList.size(); i++) {
            String jobName = buildList.get(i).getJobName();
            if (buildL.contains(jobName)) {
                Integer count = countmap.get(jobName) + 1;
                countmap.put(jobName, count);
            } else {
                buildL.add(jobName);
                countmap.put(jobName, 1);
            }
        }
        return countmap;
    }

    /**
     * Get monthly total build count.
     */
    public List<Map<String, String>> countMonthlyBuild(
            List<ConcourseBuild> buildList) throws IOException {
        List<String> buildL = new ArrayList<String>();
        List<Map<String, String>> countMonthlyBuild = new ArrayList<Map<String, String>>();
        final DateFormat dateformat = new SimpleDateFormat("M");
        Date date = new Date();
        Integer currentMonth = Integer.parseInt(dateformat.format(date));
        Integer startTimeMonth;
        for (int i = 0; i < buildList.size(); i++) {
            String jobName = buildList.get(i).getJobName();
            if (buildList.get(i).getStartTime() != null) {
                long startTime = Integer.parseInt(buildList.get(i).getStartTime());
                Date startTimeFormat = new Date(startTime * 1000);
                startTimeMonth = Integer.parseInt(dateformat.format(startTimeFormat));
                System.out.println("------------>startTimeMonth:" + startTimeMonth);
            } else {
                startTimeMonth = 12;
                System.out.println("------------>startTimeMonth:" + startTimeMonth);
            }

            if (buildL.contains(jobName)) {
                if (currentMonth.equals(startTimeMonth)) {
                    for (int j = 0; j < countMonthlyBuild.size(); j++) {
                        String deploymentName = countMonthlyBuild.get(j).get("deployment-name");
                        if (jobName.equals(deploymentName)) {
                            Integer currentMonthcount = Integer.parseInt(
                                    countMonthlyBuild.get(j).get("currentMonth"));
                            String newcurrentMonthcount = Integer.toString(currentMonthcount + 1);
                            countMonthlyBuild.get(j).put("currentMonth", newcurrentMonthcount);
                        }
                    }
                } else if (currentMonth.equals(startTimeMonth + 1)) {
                    for (int j = 0; j < countMonthlyBuild.size(); j++) {
                        String deploymentName = countMonthlyBuild.get(j).get("deployment-name");
                        if (jobName.equals(deploymentName)) {
                            Integer previousMonthcount = Integer
                                    .parseInt(countMonthlyBuild.get(j).get("previousMonth"));
                            String newpreviousMonthcount = Integer.toString(previousMonthcount + 1);
                            countMonthlyBuild.get(j).put("previousMonth", newpreviousMonthcount);
                        }
                    }
                }
            } else {
                if (currentMonth.equals(startTimeMonth)) {
                    Map<String, String> jobmonthlycount = new HashMap<String, String>();
                    jobmonthlycount.put("deployment-name", jobName);
                    jobmonthlycount.put("currentMonth", "1");
                    jobmonthlycount.put("previousMonth", "0");
                    buildL.add(jobName);
                    countMonthlyBuild.add(jobmonthlycount);
                } else if (currentMonth.equals(startTimeMonth + 1)) {
                    Map<String, String> jobmonthlycount = new HashMap<String, String>();
                    jobmonthlycount.put("deployment-name", jobName);
                    jobmonthlycount.put("currentMonth", "0");
                    jobmonthlycount.put("previousMonth", "1");
                    buildL.add(jobName);
                    countMonthlyBuild.add(jobmonthlycount);
                }
            }
        }

        return countMonthlyBuild;

    }

    /**
     * Get build status.
     */
    public List<Map<String, String>> countBuildStatus(
            List<ConcourseBuild> buildList) throws IOException {
        List<String> buildL = new ArrayList<String>();
        List<Map<String, String>> countBuildStatus = new ArrayList<Map<String, String>>();
        final DateFormat dateformat = new SimpleDateFormat("M");
        Date date = new Date();
        Integer currentMonth = Integer.parseInt(dateformat.format(date));
        Integer startTimeMonth;
        for (int i = 0; i < buildList.size(); i++) {
            String jobName = buildList.get(i).getJobName();
            String status = buildList.get(i).getStatus();
            if (!status.equals("succeeded")) {
                status = "failed";
            }
            String jobmapstatus = jobName + status;
            if (buildList.get(i).getStartTime() != null) {
                long startTime = Integer.parseInt(buildList.get(i).getStartTime());
                Date startTimeFormat = new Date(startTime * 1000);
                startTimeMonth = Integer.parseInt(dateformat.format(startTimeFormat));
            } else {
                startTimeMonth = 12;
            }
            if (buildL.contains(jobmapstatus)) {
                if (currentMonth.equals(startTimeMonth)) {
                    for (int j = 0; j < countBuildStatus.size(); j++) {
                        String deploymentName = countBuildStatus.get(j).get("deployment-name");
                        String deploymentStatus = countBuildStatus.get(j).get("status");
                        if (jobName.equals(deploymentName) && status.equals(deploymentStatus)) {
                            Integer currentMonthcount = Integer.parseInt(
                                    countBuildStatus.get(j).get("currentMonth"));
                            String newcurrentMonthcount = Integer.toString(currentMonthcount + 1);
                            countBuildStatus.get(j).put("currentMonth", newcurrentMonthcount);
                        }
                    }
                } else if (currentMonth.equals(startTimeMonth + 1)) {
                    for (int j = 0; j < countBuildStatus.size(); j++) {
                        String deploymentName = countBuildStatus.get(j).get("deployment-name");
                        String deploymentStatus = countBuildStatus.get(j).get("status");
                        if (jobName.equals(deploymentName) && status.equals(deploymentStatus)) {
                            Integer previousMonthcount = Integer.parseInt(
                                    countBuildStatus.get(j).get("previousMonth"));
                            String newpreviousMonthcount = Integer.toString(previousMonthcount + 1);
                            countBuildStatus.get(j).put("previousMonth", newpreviousMonthcount);
                        }
                    }
                }
            } else {
                if (currentMonth.equals(startTimeMonth)) {
                    Map<String, String> jobmonthlycount = new HashMap<String, String>();
                    jobmonthlycount.put("deployment-name", jobName);
                    jobmonthlycount.put("currentMonth", "1");
                    jobmonthlycount.put("previousMonth", "0");
                    jobmonthlycount.put("status", status);
                    buildL.add(jobmapstatus);
                    countBuildStatus.add(jobmonthlycount);
                } else if (currentMonth.equals(startTimeMonth + 1)) {
                    Map<String, String> jobmonthlycount = new HashMap<String, String>();
                    jobmonthlycount.put("deployment-name", jobName);
                    jobmonthlycount.put("currentMonth", "0");
                    jobmonthlycount.put("previousMonth", "1");
                    jobmonthlycount.put("status", status);
                    buildL.add(jobmapstatus);
                    countBuildStatus.add(jobmonthlycount);
                }
            }
        }

        return countBuildStatus;

    }

    /**
     * Customize http headers.
     */
    private HttpHeaders createAuthorizationHeader(String username, String password) {
        return new HttpHeaders() {
            private static final long serialVersionUID = 3804439706099215627L;
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.getEncoder().encode(
                        auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }
}
