package com.trycatchus.echoo.exception;

public class InvalidTokenException extends ApplicationException {
    public InvalidTokenException() {
        super(403, "Invalid or expired token.");
    }
    public InvalidTokenException(String message) {
        super(403, message);
    }
}