package com.trycatchus.echoo.exceptions;

public class UnauthorizedException extends ApplicationException {
    public UnauthorizedException() {
        super(401, "Unauthorized");
    }
    public UnauthorizedException(String message) {
        super(401, message);
    }
    
}
