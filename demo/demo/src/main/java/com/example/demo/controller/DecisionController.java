package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.GameState;
import com.example.demo.model.FinalState;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class DecisionController {


    public void notifyTrainingComplete() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://127.0.0.1:4999/receive-training-complete"; 
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            System.out.println(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Sends the Game State to the Python Service
     * @param gameState The current Game State
     * @return the Action taken by the python service
     */
    public String sendGameState(GameState gameState) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(gameState);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
            String url = "http://127.0.0.1:4999/receive-game-state"; 
            String action = restTemplate.postForObject(url, request, String.class);
            action = action.toUpperCase();
            return action;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "error with sending game state";
    }

    /**
     * sends the Final State to the Python Service
     * @param gameState the final state of the game state
     */
    public void sendFinalState(FinalState gameState) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(gameState);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
            String url = "http://127.0.0.1:4999/receive-final-state";  
            restTemplate.postForObject(url, request, String.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }






}
