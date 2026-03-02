package org.example.chapter05_2;

/** 정액 할인 — 고정 금액을 차감한다. */
public class FixedDiscountPolicy implements DiscountPolicy {

    private final int discountAmount;

    public FixedDiscountPolicy(int discountAmount) {
        if (discountAmount < 0) {
            throw new IllegalArgumentException("할인 금액은 0 이상이어야 합니다: " + discountAmount);
        }
        this.discountAmount = discountAmount;
    }

    @Override
    public int apply(int price) {
        return Math.max(0, price - discountAmount);
    }

    @Override
    public String describe() {
        return "정액 할인 (" + discountAmount + "원)";
    }
}
