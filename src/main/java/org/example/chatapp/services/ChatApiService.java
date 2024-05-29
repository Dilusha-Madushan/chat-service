package org.example.chatapp.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatApiService {

    @Value("${api.key}")
    private String apiKey;

    public String getResponseFromBot(String userMessage) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare payload
        String payload = String.format("{\"contents\": [{\"parts\":[{\"text\": \"%s\"}]}]}", userMessage);

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);

        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Send POST request
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // Check the response status and parse the response
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return extractTextFromJson(response.getBody());
            }
        } catch (Exception e) {
            // Log the error or handle it as necessary
            System.out.println("Error during API request: " + e.getMessage());
        }
        // Return null if there is a failure or an exception
        return null;
    }

    private String extractTextFromJson(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode candidatesNode = rootNode.path("candidates");
            if (!candidatesNode.isArray() || candidatesNode.isEmpty()) {
                return null;
            }
            JsonNode contentNode = candidatesNode.get(0).path("content");
            JsonNode partsNode = contentNode.path("parts");
            if (!partsNode.isArray() || partsNode.isEmpty()) {
                return null;
            }
            JsonNode textNode = partsNode.get(0).path("text");
            if (textNode.isMissingNode()) {
                return null;
            }
            return textNode.asText();
        } catch (Exception e) {
            // Log the error or handle it as necessary
            System.out.println("Error parsing JSON response: " + e.getMessage());
            return null;
        }
    }
}
