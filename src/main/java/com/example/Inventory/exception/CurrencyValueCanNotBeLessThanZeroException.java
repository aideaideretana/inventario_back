package com.example.Inventory.exception;

public class CurrencyValueCanNotBeLessThanZeroException extends RuntimeException {

    public CurrencyValueCanNotBeLessThanZeroException(String message) {
        super(message);
    }

}
