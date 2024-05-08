package com.varit.backend.assessment.testutil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
