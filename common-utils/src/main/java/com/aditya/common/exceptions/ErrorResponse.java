package com.aditya.common.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    private String error;

    public ErrorResponse(String error) {
        this.error = error;
    }
}
