package com.techmath.example.config;

import com.techmath.allcrud.exception.handler.AbstractGlobalExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Custom controller advice extending Allcrud's base exception handler.
 * All standard CRUD exception mappings are inherited.
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends AbstractGlobalExceptionHandler {
    
    // Custom exception handlers can be added here
    // All CRUD common exception handlers are already implemented by AbstractControllerAdvice
    
}
