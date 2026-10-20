package com.example.bdd.api.model;

public class ApiResponse {
    private final int responseCode;
    private final String message;

    public ApiResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }
}
