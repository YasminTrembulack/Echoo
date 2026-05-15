package com.trycatchus.echoo.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.trycatchus.echoo.exceptions.InvalidEnumValueException;

public final class EnumUtils {

    private EnumUtils() {}

    public static <T extends Enum<T>> T fromString(
            Class<T> enumClass,
            String value
    ) {

        validateValue(value, enumClass);

        String normalized = normalize(value);

        try {
            return Enum.valueOf(enumClass, normalized);

        } catch (IllegalArgumentException ex) {

            throw new InvalidEnumValueException(
                    value,
                    enumClass.getSimpleName(),
                    getAllowedValues(enumClass)
            );
        }
    }

    private static <T extends Enum<T>> void validateValue(
            String value,
            Class<T> enumClass
    ) {

        if (value == null || value.isBlank()) {

            throw new InvalidEnumValueException(
                    value,
                    enumClass.getSimpleName(),
                    getAllowedValues(enumClass)
            );
        }
    }

    private static String normalize(String value) {

        return value
                .trim()
                .replace("-", "_")
                .replace(" ", "_")
                .toUpperCase();
    }

    private static <T extends Enum<T>> String getAllowedValues(
            Class<T> enumClass
    ) {

        return Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}