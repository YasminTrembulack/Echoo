package com.trycatchus.echoo.exception;

public class KeySpecException extends ApplicationException {
    public KeySpecException() {
        super(500, "Key specification incorrectly set in server.");
    }
    public KeySpecException(String message) {
        super(500, message);
    }
}