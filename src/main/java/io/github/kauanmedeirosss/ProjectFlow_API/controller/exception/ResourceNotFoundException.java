package io.github.kauanmedeirosss.ProjectFlow_API.controller.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
