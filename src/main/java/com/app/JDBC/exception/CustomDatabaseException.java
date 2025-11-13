package com.app.JDBC.exception;

public class CustomDatabaseException extends Exception {
    public CustomDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
