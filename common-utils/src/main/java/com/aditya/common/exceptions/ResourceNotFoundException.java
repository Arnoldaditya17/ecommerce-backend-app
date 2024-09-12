package com.aditya.common.exceptions;

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(String entityNotFound,String id) {

        super(entityNotFound + " with ID " + id + " not found");
    }

}
