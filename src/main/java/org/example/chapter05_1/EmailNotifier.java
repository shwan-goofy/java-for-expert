package org.example.chapter05_1;

/**
 * SRP 적용 — EmailNotifier는 이메일 알림만 담당한다.
 */
public class EmailNotifier {

    public void sendOrderConfirmation(Order order) {
        System.out.println("[Email] 주문 확인 이메일 발송");
        System.out.println("  수신: " + order.getCustomerEmail());
        System.out.println("  주문번호: " + order.getOrderId());
        System.out.println("  상품: " + order.getItems());
        System.out.println("  합계: " + order.getTotalPrice() + "원");
    }

    public void sendOrderCancellation(Order order) {
        System.out.println("[Email] 주문 취소 이메일 발송 → " + order.getCustomerEmail());
    }
}
