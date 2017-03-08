package com.alfrescos.orderingsystem.security;

import javassist.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liger on 11/10/2016.
 */
@ControllerAdvice
public class HandleException {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleCustomException(AccessDeniedException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 401);
        metadata.put("message","Unauthorized");
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<?> handleCustomException(AuthenticationCredentialsNotFoundException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 403);
        metadata.put("message","Forbidden");
//        metadata.put("Test key", "Account not found!");
        return new ResponseEntity<Object>(metadata, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleCustomException(MethodArgumentNotValidException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 400);
        metadata.put("message","Bad Request");
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleCustomException(ConstraintViolationException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 422);
        metadata.put("message","Unprocessable Entity");
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<?> handleCustomException(EntityExistsException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 409);
        metadata.put("message","Conflict");
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleCustomException(NotFoundException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 404);
        metadata.put("message","Not Found");
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleCustomException(EntityNotFoundException ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 404);
        metadata.put("message","Not Found Entity");
        return new ResponseEntity<Object>(metadata, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleCustomException(Exception ex) {
        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("status", 500);
        metadata.put("message","Internal Server Error");
        return new ResponseEntity<Object>(metadata, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
