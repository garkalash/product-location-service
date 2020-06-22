package com.jazva.challenge.exception.impl;

import static java.lang.String.format;

public class InvalidQuantityException extends RuntimeException {
    private static final String INVALID_QUANTITY_EX_MESSAGE = "For product with id: %d in location with id: %d, the quantity %d is not valid.";

    public InvalidQuantityException(Long productId, Long locationId, Integer qty) {
        super(format(INVALID_QUANTITY_EX_MESSAGE, productId, locationId, qty));
    }
}
