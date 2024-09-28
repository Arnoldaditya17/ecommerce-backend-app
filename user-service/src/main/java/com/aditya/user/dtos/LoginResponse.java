package com.aditya.user.dtos;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class LoginResponse {

    private String accessToken;

    private long expiresIn;

    private String refreshToken;

    public LoginResponse setToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public LoginResponse setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public LoginResponse setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

}