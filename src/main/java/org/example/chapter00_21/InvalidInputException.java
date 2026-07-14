package org.example.chapter00_21;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause); // 원인(cause)을 보존
    }
}
