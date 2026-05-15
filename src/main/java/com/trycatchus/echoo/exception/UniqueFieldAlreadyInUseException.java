package com.trycatchus.echoo.exception;

import java.util.List;

public class UniqueFieldAlreadyInUseException extends ApplicationException {

    public UniqueFieldAlreadyInUseException(Class<?> entity, List<String> fieldErrors) {
        super(409,
                entity.getSimpleName().toString()
                        + " with the following fields already in use: "
                        + String.join("; ", fieldErrors));
    }
}