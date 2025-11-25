package io.jenkins.plugins;

import hudson.model.TaskListener;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiService {

    public static boolean triggerScan(String token, String targetFile, TaskListener listener) {
        try {
            URL url = new URL("https://devmiddleware.vigilnz.com/scan-targets/create");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cookie", "__stripe_mid=8049620b-fc23-4fa0-bef6-2f457e454c507c1186; express.sid=s%3ASI1hdS10BJDFcC8Uh56X2kAS_5s_DyCA.Wrv4dKq4xj9ANv4B0EcTVtlCD96CRCK42XjjrAT2FqQ; cf_clearance=UnXvGmt.bZ52lZ.FJ78KsgODIH4Rq6f2_ocM0R8dEnw-1764075148-1.2.1.1-N.oIfJmGZpOHjfT23xgSNnJOOrCttLWtkNAC.Zth32AjfpBCn4RfCKLlRBINS3ES1AfmTiK3Mj8JK8FD8D5z9cC.1hQQJSUYrLBEJ.502c6SCnQPcui_le_Q2cFvlONWOaUdXnQje.RJceuOu1jXk7cNTd272Uc7hlbI5j6mYFaiBqPlrXfi3NJwnrsNWWZnvMNZvTV9Eo2q4LfKVW2RMKp8L3L5b5A.bw.4JID7DjM");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("repositoryId", "69148c059c852f7dbff13d0c");
            json.put("scanType", "cve");
            json.put("repoName", "awesome-go");
            json.put("gitRepoUrl", "https://github.com/avelino/awesome-go.git");
            json.put("organization", "Go Projects");
            json.put("language", "unknown");
            json.put("status", "active");

            String body = json.toString();

            listener.getLogger().println("API URL: " + conn.getURL());
            listener.getLogger().println("Method: " + conn.getRequestMethod());
            listener.getLogger().println("Headers: Content-Type=" + conn.getRequestProperty("Content-Type"));
            listener.getLogger().println("Cookies: " + conn.getRequestProperty("Cookie"));
            listener.getLogger().println("Request Body: " + body);

            listener.getLogger().println("API Run : " + conn);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.getBytes());
            }

            int responseCode = conn.getResponseCode();
            listener.getLogger().println("API Response Code: " + responseCode);

            // Print the response to output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream()))) {

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                listener.getLogger().println("API Response Body: " + response.toString());
            }

            return responseCode == 200;

        } catch (Exception e) {
            listener.getLogger().println("API Error: " + e.getMessage());
            return false;
        }
    }

}
