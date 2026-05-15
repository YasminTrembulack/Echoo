package com.trycatchus.echoo.exceptions;

public class InvalidEnumValueException extends ApplicationException {

    public InvalidEnumValueException(
            String value,
            String enumName,
            String allowedValues
    ) {        
        super(400, "Invalid value '%s' for enum %s. Allowed values: [%s]"
            .formatted(value, enumName, allowedValues)
        );    
    }
}