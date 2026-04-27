package com.trycatchus.echoo.enums;

public enum OrderStatus {
    PENDING,
    PAID,
    CANCELLED,
    REFUNDED;

    public static OrderStatus fromString(String status) {
        return OrderStatus.valueOf(status.toUpperCase());
    }
}
