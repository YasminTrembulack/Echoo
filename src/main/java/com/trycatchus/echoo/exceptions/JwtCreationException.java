package com.trycatchus.echoo.exceptions;

public class JwtCreationException extends ApplicationException {
    public JwtCreationException() {
        super(500, "Claims couldn't be converted.");
    }
    public JwtCreationException(String message) {
        super(500, message);
    }
}