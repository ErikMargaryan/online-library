package com.library.rest.advice;

import com.library.exception.ErrorDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestControllerAdvice
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        val details = new ArrayList<String>();
        details.add(ex.getMessage());

        val error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getClass().getSimpleName(),
                details,
                request.getDescription(false));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        val details = new ArrayList<String>();
        details.add(ex.getLocalizedMessage());

        val error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getClass().getSimpleName(),
                details,
                request.getDescription(false));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        val details = new ArrayList<String>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            details.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        val error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getClass().getSimpleName(),
                details,
                request.getDescription(false));
        return ResponseEntity.badRequest().body(error);
    }
}
