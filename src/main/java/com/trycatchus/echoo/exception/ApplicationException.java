package com.trycatchus.echoo.exception;

import org.springframework.http.HttpStatusCode;

import com.trycatchus.echoo.dto.responses.ErrorResponse;


public class ApplicationException extends RuntimeException {
    private HttpStatusCode statusCode;
    private ErrorResponse errorResponse;

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ApplicationException(
            int statusCode,
            String message
    ) {
        super();
        this.statusCode = HttpStatusCode.valueOf(statusCode);
        this.errorResponse = new ErrorResponse(message);
    }

    public ApplicationException(
            int statusCode,
            String message,
            Throwable cause
    ) {
        super(cause);
        this.statusCode = HttpStatusCode.valueOf(statusCode);
        this.errorResponse = new ErrorResponse(message);
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}