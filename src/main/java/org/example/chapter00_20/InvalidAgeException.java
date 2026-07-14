package org.example.chapter00_20;

// 체크 예외 — RuntimeException을 상속하지 않으므로 호출부에서 반드시 처리해야 한다
public class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}
