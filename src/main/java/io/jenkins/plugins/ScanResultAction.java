package io.jenkins.plugins;

import com.google.gson.Gson;
import hudson.model.Action;
import io.jenkins.plugins.models.ApiResponse;

public class ScanResultAction implements Action {
    private final ApiResponse response;

    public ScanResultAction(String scanSummary) {

        // Convert JSON string to ApiResponse
        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(scanSummary, ApiResponse.class);
        this.response = apiResponse;
    }

    @Override
    public String getIconFileName() {
        return "clipboard.png"; // or a custom icon
    }

    @Override
    public String getDisplayName() {
        return "Vigilnz Scan Results";
    }

    @Override
    public String getUrlName() {
        return "vigilnzResult"; // URL path under build/job
    }

    public ApiResponse getResponse() {
        return response;
    }
}
