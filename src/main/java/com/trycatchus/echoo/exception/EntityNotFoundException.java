package com.trycatchus.echoo.exception;

public class EntityNotFoundException extends ApplicationException {
    public EntityNotFoundException() {
        super(404, "Entity not found.");
    }
    public EntityNotFoundException(String message) {
        super(404, message);
    }

    public EntityNotFoundException(Class<?> entityName ) {
        super(404, entityName.getSimpleName().toString() + " not found.");
    }
}