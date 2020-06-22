package com.jazva.challenge.exception.impl;

public class EntityNotFoundException extends RuntimeException {
    private static final String INVALID_ID_EXCEPTION = "The requested entity doesn't exist, please review the id.";

    public EntityNotFoundException(){
        super(INVALID_ID_EXCEPTION);
    }

}
