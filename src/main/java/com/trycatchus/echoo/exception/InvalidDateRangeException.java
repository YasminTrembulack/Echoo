package com.trycatchus.echoo.exception;

public class InvalidDateRangeException extends ApplicationException {
    public InvalidDateRangeException() {
        super(400, "Invalid date range.");
    }
    public InvalidDateRangeException(String message) {
        super(400, message);
    }
    
}
