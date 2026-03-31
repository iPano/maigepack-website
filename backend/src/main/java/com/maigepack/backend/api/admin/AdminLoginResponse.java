package com.maigepack.backend.api.admin;

public class AdminLoginResponse {
    private final String token;
    private final String tokenType;

    public AdminLoginResponse(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}

