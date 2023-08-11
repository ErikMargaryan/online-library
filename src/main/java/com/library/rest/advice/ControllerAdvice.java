package com.library.rest.advice;

import com.library.exception.ErrorDetails;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getClass().getSimpleName(),
                details,
                request.getDescription(false));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        List<String> details = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            details.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getClass().getSimpleName(),
                details,
                request.getDescription(false));
        return ResponseEntity.badRequest().body(error);
    }
}
