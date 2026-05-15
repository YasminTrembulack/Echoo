package com.trycatchus.echoo.exceptions;

import com.trycatchus.echoo.dtos.responses.ErrorResponse;

public class ApplicationException extends RuntimeException {
    private ErrorResponse errorResponse;

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ApplicationException(
            int statusCode,
            String message
    ) {
        super();
        this.errorResponse = new ErrorResponse(statusCode, message);
    }

    public ApplicationException(
            int statusCode,
            String message,
            Throwable cause
    ) {
        super(cause);
        this.errorResponse = new ErrorResponse(statusCode,message);
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}