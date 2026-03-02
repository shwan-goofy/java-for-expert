package org.example.chapter05_2;

import java.util.List;

public class OcpMain {

    // OCP 위반 — 새 타입 추가 시 이 메서드를 수정해야 함
    static int calculatePriceViolation(int price, String type) {
        if ("RATE10".equals(type)) return (int) (price * 0.9);
        if ("FIXED1000".equals(type)) return price - 1000;
        // 새 타입 추가 시 여기에 if 추가 → OCP 위반
        return price;
    }

    // OCP 적용 — DiscountPolicy 인터페이스에 의존
    static int calculatePrice(int price, DiscountPolicy policy) {
        return policy.apply(price);
    }

    public static void main(String[] args) {
        demonstrateViolation();
        demonstrateOCP();
    }

    static void demonstrateViolation() {
        System.out.println("=== OCP 위반 ===");
        System.out.println("10% 할인: " + calculatePriceViolation(50000, "RATE10"));
        System.out.println("1000원 할인: " + calculatePriceViolation(50000, "FIXED1000"));
        System.out.println("→ 새 정책 추가 시 calculatePriceViolation() 수정 필요\n");
    }

    static void demonstrateOCP() {
        System.out.println("=== OCP 적용 ===");

        int originalPrice = 50000;

        List<DiscountPolicy> policies = List.of(
            new RateDiscountPolicy(0.10),
            new RateDiscountPolicy(0.20),
            new FixedDiscountPolicy(1000),
            new FixedDiscountPolicy(5000),
            // 새 정책 추가 — 기존 calculatePrice() 수정 없이 여기에만 추가
            price -> Math.max(0, price - (price > 30000 ? 3000 : 0))  // 람다로 즉석 정책
        );

        for (DiscountPolicy policy : policies) {
            int discounted = calculatePrice(originalPrice, policy);
            System.out.printf("%-20s: %d원 → %d원%n",
                    policy.describe(), originalPrice, discounted);
        }
        System.out.println("→ calculatePrice()는 수정 없이 새 정책이 추가됨");
    }
}
