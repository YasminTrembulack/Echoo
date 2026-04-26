package com.trycatchus.echoo.filters;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.trycatchus.echoo.dto.responses.ErrorResponse;
import com.trycatchus.echoo.exception.ApplicationException;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ApplicationException.class })
    protected ResponseEntity<ErrorResponse> handleConflict(
            ApplicationException ex,
            WebRequest request
    ) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getErrorResponse());
    }
}