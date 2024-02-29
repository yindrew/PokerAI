package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Action;
import com.example.demo.model.GameState;
import com.example.demo.model.Output;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class DecisionController {


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
        return "error";
    }


    public String sendFinalState(Output gameState) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(gameState);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);
            String url = "http://127.0.0.1:4999/receive-final-state";  
            String action = restTemplate.postForObject(url, request, String.class);
            return action;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "error";
    }






}
