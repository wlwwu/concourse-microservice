# BICS DASHBOARD SERVICES INSTRUCTIONS #

## HOW TO BUILD ##
**./gradlew clean build**

## BUILD location ##
**~/bics-dashboard-services/build/libs#**

## Test ##
**./gradlew test**

## How to check test coverage ##
we use reports provided by jacoco tool to check testing coverage

**./gradlew  test jacocoTestCoverageVerification**
**./gradlew  test jacocoTestReport**

We can find code coverage reports from **~/bics-dashboard-services/build/jacocoHtml#**