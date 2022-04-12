package common.exceptions;

import enums.ErrorCodes;

public class BusinessException extends RuntimeException{
    private final String message;
    private final Object param;
    private final ErrorCodes errorCode;
    private final Throwable exception;

    public BusinessException(String message) {
        this(message, null);
    }

    public BusinessException(String message, ErrorCodes errorCode) {
        this(message, errorCode, null);
    }

    public BusinessException(String message, ErrorCodes errorCode, Object param) {
        this(message, errorCode, param, null);
    }

    public BusinessException(String message, ErrorCodes errorCode, Object param, Throwable exception ) {
        this.message = message;
        this.errorCode = errorCode;
        this.param = param;
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "Business exception occurred: "
            + message
            + (param != null ? param : "")
            + (errorCode != null ? errorCode : "")
            + (exception != null ? exception.getMessage() : "");
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getParam() {
        return param;
    }

    public ErrorCodes getErrorCode() {
        return errorCode;
    }

    public Throwable getException() {
        return exception;
    }
}
