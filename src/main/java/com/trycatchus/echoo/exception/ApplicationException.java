package com.trycatchus.echoo.exception;

import com.trycatchus.echoo.dto.responses.ErrorResponse;

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