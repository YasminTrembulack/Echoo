package com.trycatchus.echoo.exceptions;

public class InvalidDateRangeException extends ApplicationException {
    public InvalidDateRangeException() {
        super(400, "Invalid date range: startDate must be before endDate.");
    }
    public InvalidDateRangeException(String message) {
        super(400, message);
    }
    
}
