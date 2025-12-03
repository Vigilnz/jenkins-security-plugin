package io.jenkins.plugins.models;

import java.util.List;
import java.util.Map;

public class ApiResponse {

    private String gitHubUrl;
    private String totalRequested;
    private String successfulResults;
    private String failedResults;
    private List<String> scanTypes;
    private List<Map<String, Object>> results;

    // No-args constructor (required for JSON mapping libraries like Jackson/Gson)
    public ApiResponse() {
    }

    // Full-args constructor
    public ApiResponse(String gitHubUrl,
                       String totalRequested,
                       String successfulResults,
                       String failedResults,
                       List<String> scanTypes,
                       List<Map<String, Object>> results) {
        this.gitHubUrl = gitHubUrl;
        this.totalRequested = totalRequested;
        this.successfulResults = successfulResults;
        this.failedResults = failedResults;
        this.scanTypes = scanTypes;
    }

    // Getter and Setter for gitHubUrl
    public String getGitHubUrl() {
        return gitHubUrl;
    }

    public void setGitHubUrl(String gitHubUrl) {
        this.gitHubUrl = gitHubUrl;
    }

    // Getter and Setter for totalRequested
    public String getTotalRequested() {
        return totalRequested;
    }

    public void setTotalRequested(String totalRequested) {
        this.totalRequested = totalRequested;
    }

    // Getter and Setter for successfulResults
    public String getSuccessfulResults() {
        return successfulResults;
    }

    public void setSuccessfulResults(String successfulResults) {
        this.successfulResults = successfulResults;
    }

    // Getter and Setter for failedResults
    public String getFailedResults() {
        return failedResults;
    }

    public void setFailedResults(String failedResults) {
        this.failedResults = failedResults;
    }

    // Getter and Setter for scanTypes
    public List<String> getScanTypes() {
        return scanTypes;
    }

    public void setScanTypes(List<String> scanTypes) {
        this.scanTypes = scanTypes;
    }

    // Getter and Setter for results
    public List<Map<String, Object>> getResults() {
        return results;
    }

    public void setResults(List<Map<String, Object>> results) {
        this.results = results;
    }
}
