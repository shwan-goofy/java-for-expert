package org.example.chapter05_5;

/** 이메일 발송 구현체 — 저수준 모듈. */
public class EmailSender implements MessageSender {

    @Override
    public void send(String recipient, String message) {
        System.out.println("[EmailSender] → " + recipient + " : " + message);
    }
}
