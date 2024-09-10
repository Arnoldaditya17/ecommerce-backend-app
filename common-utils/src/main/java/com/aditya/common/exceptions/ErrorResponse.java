package com.aditya.common.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    private String error;
    private int statusCode;



    public ErrorResponse( String error, int statusCode) {
        this.statusCode = statusCode;
        this.error = error;

    }
}
