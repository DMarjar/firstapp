package mx.edu.utez.firstapp.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
// @ControllerAdvice is used to handle exceptions across the whole application in one global handling component
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) { // For each error in the list of exceptions
            errors.add(error.getField() + ": " + error.getDefaultMessage()); // Add the error to the list of errors
        }
        CustomResponse<Object> apiError = new CustomResponse<>(errors, true, 400, "Validate fields"); // Create a new CustomResponse object
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request); // Return the exception
    }
}
