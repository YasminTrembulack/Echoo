package com.trycatchus.echoo.exceptions;

public class PasswordValidationException extends ApplicationException {
    public PasswordValidationException(Integer errorCode, String message) {
        super(errorCode, message);
    }
    
}
