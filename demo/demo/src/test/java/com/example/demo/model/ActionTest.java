package com.example.demo.model;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionTest {
    Action action;

    @BeforeEach
    void beforeEach() {

    }


    @Test
    void testActionCast() {
        String test = "BET_BIG";
        action = Action.valueOf(test);
        System.out.println(action);
    }
}
