package com.testapp.problem2.utils;

/**
 * @author shibin
 * @version 1.0
 * @date 31/03/18
 */

public class AppException extends RuntimeException {

    private int errorCode;

    private String errorMessage;

    public AppException(final int errorCode, final String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static final int ERROR_ACCESS_DENIED             =   400;
    public static final int ERROR_SERVICE_NOT_AVAILABLE     =   1000;
}
