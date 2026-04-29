package com.trycatchus.echoo.utils;

public class UpdateUtils {
    public static <T> T valueOrKeep(T newValue, T currentValue) {
        return newValue != null ? newValue : currentValue;
    }
}