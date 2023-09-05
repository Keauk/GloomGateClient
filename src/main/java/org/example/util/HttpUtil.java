package org.example.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpUtil {
    private static final String BASE_URL = "http://localhost:8080"; // Update with your server's URL

    public static JSONObject register(String username, String password) {
        return sendPostRequest("/api/users/register", username, password);
    }

    public static JSONObject authenticateUser(String username, String password) {
        return sendPostRequest("/api/users/login", username, password);
    }

    private static JSONObject sendPostRequest(String endpoint, String username, String password) {
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(BASE_URL + endpoint);

            // Set request headers and body
            post.setHeader("Content-type", "application/json");
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
            post.setEntity(new StringEntity(json.toString()));

            // Execute and parse response
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            if (result.trim().startsWith("{") && result.trim().endsWith("}")) {
                return new JSONObject(result);
            } else {
                System.out.println("Received non-JSON response: " + result);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}