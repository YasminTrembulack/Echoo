package com.trycatchus.echoo.enums;

public enum PaymentMethod {
    CREDIT_CARD,
    DEBIT_CARD,
    PAYPAL,
    BANK_TRANSFER;

    public static PaymentMethod fromString(String method) {
        return PaymentMethod.valueOf(method.toUpperCase());
    }

}