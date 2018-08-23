package com.bosch.bics.dashboard.bicsdashboardservices.controllers;

import com.bosch.bics.dashboard.bicsdashboardservices.models.ConcourseBuild;
import com.bosch.bics.dashboard.bicsdashboardservices.services.GetBuildCount;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConcourseController {
    @Autowired
    private GetBuildCount getbuildCount;

    /**
     * Total count of builds.
     */
    @RequestMapping(value = "/v1/buildcount",
            method = RequestMethod.GET, produces = "application/json")
    public Map<String, Integer> buildcount() throws IOException {
        List<ConcourseBuild> buildList = getbuildCount.getBuild();
        Map<String, Integer> countMap = getbuildCount.countBuild(buildList);
        return countMap;
    }

    /**
     * Deployment of frequency.
     */
    @RequestMapping(value = "/v1/deploymentfrequency", 
            method = RequestMethod.GET, produces = "application/json")
    public List<Map<String,String>> buildmonthcount() throws IOException {
        List<ConcourseBuild> buildList = getbuildCount.getBuild();
        List<Map<String,String>> buildmonthcount = getbuildCount.countMonthlyBuild(buildList);

        return buildmonthcount;
    }
    
    /**
     * Deployment status of frequency.
     */
    @RequestMapping(value = "/v1/deploymentstatusfrequency",
            method = RequestMethod.GET, produces = "application/json")
    public List<Map<String,String>> buildstatuscount() throws IOException {
        List<ConcourseBuild> buildList = getbuildCount.getBuild();
        List<Map<String,String>> buildstatuscount = getbuildCount.countBuildStatus(buildList);
        return buildstatuscount;
    }
}
