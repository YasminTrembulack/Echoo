package com.trycatchus.echoo.exception;

public class PasswordValidationException extends ApplicationException {
    public PasswordValidationException(Integer errorCode, String message) {
        super(errorCode, message);
    }
    
}
