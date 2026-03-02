package org.example.chapter04_3;

import java.util.List;

public class AbstractMain {

    public static void main(String[] args) {
        demonstrateAbstractClass();
        demonstrateInterface();
        demonstrateFunctionalInterface();
        demonstrateSealedInterface();
    }

    // ----------------------------------------------------------------
    // 1. abstract class — 다형성으로 공통 인터페이스 사용
    // ----------------------------------------------------------------
    static void demonstrateAbstractClass() {
        System.out.println("=== abstract class ===");

        var card = new CreditCardPayment(
                "PAY-001", "홍길동", "1234567890123456", 500000);

        System.out.println("결제 타입: " + card.getPaymentType());
        System.out.println("카드번호 (마스킹): " + card.getMaskedCardNumber());

        card.pay(150000);
        card.pay(400000);  // 한도 초과

        // 부모 타입으로 다형성
        Payment.AbstractPayment payment = card;
        System.out.println("부모 타입 참조: " + payment.getOwnerName());
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. interface — 다중 구현, default/static 메서드
    // ----------------------------------------------------------------
    static void demonstrateInterface() {
        System.out.println("=== interface (Refundable) ===");

        var card = new CreditCardPayment(
                "PAY-002", "이몽룡", "9876543210987654", 300000);

        card.pay(100000);
        card.refund(30000);
        card.partialRefund(100000, 50);  // default 메서드

        // static 팩토리 메서드
        var noRefund = Payment.Refundable.noRefund();
        noRefund.refund(50000);
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. @FunctionalInterface + 람다
    // ----------------------------------------------------------------
    static void demonstrateFunctionalInterface() {
        System.out.println("=== @FunctionalInterface + 람다 ===");

        // 람다로 DiscountStrategy 구현
        Payment.DiscountStrategy tenPercent  = price -> (int) (price * 0.9);
        Payment.DiscountStrategy fixed1000   = price -> Math.max(0, price - 1000);
        Payment.DiscountStrategy vip20       = price -> (int) (price * 0.8);

        int originalPrice = 50000;
        System.out.println("원가: " + originalPrice);
        System.out.println("10% 할인: " + tenPercent.apply(originalPrice));
        System.out.println("1000원 할인: " + fixed1000.apply(originalPrice));
        System.out.println("VIP 20% 할인: " + vip20.apply(originalPrice));

        // 할인 체이닝 (andThen default 메서드)
        Payment.DiscountStrategy combined = tenPercent.andThen(fixed1000);
        System.out.println("10% + 1000원 합산 할인: " + combined.apply(originalPrice));
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. sealed interface + switch expression
    // ----------------------------------------------------------------
    static void demonstrateSealedInterface() {
        System.out.println("=== sealed interface (JDK 17) ===");

        List<Shape.GeometricShape> shapes = List.of(
            new Shape.Circle(5.0),
            new Shape.Rectangle(4.0, 6.0),
            new Shape.Triangle(3.0, 4.0, 5.0)
        );

        for (var shape : shapes) {
            System.out.println(shape.describe());

            // sealed interface이므로 모든 허용 구현체를 처리하지 않으면 컴파일 에러
            String type = switch (shape) {
                case Shape.Circle c    -> "원 (반지름: " + c.radius() + ")";
                case Shape.Rectangle r -> "직사각형 (" + r.width() + "×" + r.height() + ")";
                case Shape.Triangle t  -> "삼각형 (변: " + t.a() + ", " + t.b() + ", " + t.c() + ")";
            };
            System.out.println("  → " + type);
        }
    }
}
