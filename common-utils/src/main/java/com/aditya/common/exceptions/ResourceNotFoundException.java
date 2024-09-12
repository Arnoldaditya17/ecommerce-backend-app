package com.aditya.common.exceptions;

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(String courseNotFound) {

        super(courseNotFound);
    }

    public ResourceNotFoundException() {
        super("product not found !");
    }
}
