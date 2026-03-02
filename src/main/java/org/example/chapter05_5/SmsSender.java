package org.example.chapter05_5;

/** SMS 발송 구현체 — 저수준 모듈. */
public class SmsSender implements MessageSender {

    @Override
    public void send(String recipient, String message) {
        System.out.println("[SmsSender] → " + recipient + " : " + message);
    }
}
