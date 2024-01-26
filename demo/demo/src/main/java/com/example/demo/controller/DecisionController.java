package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Decision;
import com.example.demo.model.GameState;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class DecisionController {


    public void sendGameState(GameState gameState) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(gameState);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
            String url = "http://127.0.0.1:5000/receive-game-state";  // Replace with the actual Python service URL
            String action = restTemplate.postForObject(url, request, String.class);
            System.out.println(action);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    
    @GetMapping("/")
    public String helloWorld() {
        System.out.println("Hello World");
        return "Hello World";
    }

    // handles the decision request from python
    @PostMapping("/python-post")
    public ResponseEntity<?> receiveDecision(@RequestBody Decision decision) {
        System.out.println(decision.getAction() + " " + decision.getValue());
        return ResponseEntity.ok("Decision received");
    }




}
