package com.aditya.common.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException(String entityNotFound, UUID id) {

        super(entityNotFound + " with ID " + id + " not found");
    }

}
