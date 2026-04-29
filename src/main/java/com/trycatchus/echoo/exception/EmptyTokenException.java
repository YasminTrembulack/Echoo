package com.trycatchus.echoo.exception;

public class EmptyTokenException extends ApplicationException {
    public EmptyTokenException() {
        super(400, "Field 'token' cannot be empty.");
    }
    public EmptyTokenException(String message) {
        super(400, message);
    }
}