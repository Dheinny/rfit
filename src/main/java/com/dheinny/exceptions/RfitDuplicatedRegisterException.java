package com.dheinny.exceptions;

/**
 * Created by dmarques on 19/01/2017.
 */
public class RfitDuplicatedRegisterException extends Exception{
    public RfitDuplicatedRegisterException() {
    }

    public RfitDuplicatedRegisterException(String message) {
        super(message);
    }

    public RfitDuplicatedRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RfitDuplicatedRegisterException(Throwable cause) {
        super(cause);
    }

    public RfitDuplicatedRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
