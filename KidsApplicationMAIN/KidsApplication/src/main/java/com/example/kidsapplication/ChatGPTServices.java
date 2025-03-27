package com.example.kidsapplication;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ChatGPTServices {
    private static final Logger LOGGER = Logger.getLogger(ChatGPTServices.class.getName());
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String apiKey;
    private final HttpClient httpClient;
    private final Gson gson;

    public ChatGPTServices(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    public String getResponse(String userMessage) {
        try {
            JsonObject requestBody = new JsonObject();
            requestBody.addProperty("model", "gpt-3.5-turbo");

            JsonObject message = new JsonObject();
            message.addProperty("role", "user");
            message.addProperty("content", userMessage);

            JsonObject[] messages = new JsonObject[]{message};
            requestBody.add("messages", gson.toJsonTree(messages));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
                return jsonResponse.getAsJsonArray("choices")
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("message")
                        .get("content")
                        .getAsString();
            } else {
                LOGGER.warning("API request failed with status code: " + response.statusCode());
                return "I'm sorry, I'm having trouble connecting right now. Please try again later.";
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Error communicating with ChatGPT API", e);
            return "I'm sorry, I'm having trouble connecting right now. Please try again later.";
        }
    }

    public void dispose() {
        // HttpClient is immutable and doesn't need to be closed
        // Just log that the service is being disposed
        LOGGER.info("ChatGPTService is being disposed");
    }
}
