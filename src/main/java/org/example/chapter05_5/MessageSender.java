package org.example.chapter05_5;

/** DIP 적용 — 추상 인터페이스. 고수준 모듈이 이것에 의존한다. */
public interface MessageSender {
    void send(String recipient, String message);
}
