package com.trycatchus.echoo.exception;

import java.util.List;

public class UniqueFieldAlreadyInUseException extends ApplicationException {

    public UniqueFieldAlreadyInUseException(String entity, List<String> fieldErrors) {
        super(409, entity + " with the following fields already in use: " + String.join("; ", fieldErrors));
    }
}