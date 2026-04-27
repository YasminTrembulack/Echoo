package com.trycatchus.echoo.filters;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.trycatchus.echoo.dto.responses.ErrorResponse;
import com.trycatchus.echoo.exception.ApplicationException;


@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException( MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(400).body(new ErrorResponse(400, errorMessage));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException ex) {
        return ResponseEntity.status(ex.getErrorResponse().statusCode()).body(ex.getErrorResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        var errorResponse = new ErrorResponse(500, "An unexpected error occurred.");
        return ResponseEntity.status(errorResponse.statusCode()).body(errorResponse);
    }
}