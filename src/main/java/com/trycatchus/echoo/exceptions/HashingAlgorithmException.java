package com.trycatchus.echoo.exceptions;

public class HashingAlgorithmException extends ApplicationException {
    public HashingAlgorithmException() {
        super(500, "Hashing algorithm incorrectly set in server.");
    }
    public HashingAlgorithmException(String message) {
        super(500, message);
    }
}