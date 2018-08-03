package com.bosch.bics.dashboard.bicsdashboardservices.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosch.bics.dashboard.bicsdashboardservices.models.ConcourseBuild;
import com.bosch.bics.dashboard.bicsdashboardservices.services.GetBuildCount;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ConcourseController {
	@Autowired
	private GetBuildCount getbuildCount;
	
	@RequestMapping(value= "/v1/buildcount", method = RequestMethod.GET, produces = "application/json")
	public Map<String, Integer> buildcount() throws IOException {
		 List<ConcourseBuild> buildList = getbuildCount.getBuild();
         Map<String, Integer> countMap = getbuildCount.countBuild(buildList);
          return countMap;
	}
}
