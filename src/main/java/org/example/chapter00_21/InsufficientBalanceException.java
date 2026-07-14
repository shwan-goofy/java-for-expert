package org.example.chapter00_21;

public class InsufficientBalanceException extends RuntimeException {
    private final int shortage;

    public InsufficientBalanceException(String message, int shortage) {
        super(message);
        this.shortage = shortage;
    }

    public int getShortage() {
        return shortage;
    }
}
