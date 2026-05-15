package com.trycatchus.echoo.exceptions;

public class PasswordMismatchException extends ApplicationException {
    public PasswordMismatchException() {
        super(400, "Passwords do not match.");
    }
    public PasswordMismatchException(String message) {
        super(400, message);
    }
}