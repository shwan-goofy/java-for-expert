package org.example.chapter05_5;

/**
 * DIP 적용 — 고수준 모듈.
 * MessageSender 인터페이스(추상)에 의존하며, 구체 구현체는 외부에서 주입받는다.
 */
public class NotificationService {

    private final MessageSender sender;  // 추상에 의존

    // 의존성 주입 (Constructor Injection)
    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void notifyOrderConfirmation(String email, String orderId) {
        sender.send(email, "주문이 확인되었습니다. 주문번호: " + orderId);
    }

    public void notifyShipping(String contact, String trackingNumber) {
        sender.send(contact, "배송이 시작되었습니다. 운송장: " + trackingNumber);
    }

    public void notifyPromotion(String contact, String promoMessage) {
        sender.send(contact, "[프로모션] " + promoMessage);
    }
}
