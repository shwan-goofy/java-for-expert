package org.example.chapter05_2;

/** 비율 할인 — 가격의 N%를 할인한다. */
public class RateDiscountPolicy implements DiscountPolicy {

    private final double discountRate;

    public RateDiscountPolicy(double discountRate) {
        if (discountRate < 0 || discountRate > 1) {
            throw new IllegalArgumentException("할인율은 0~1 사이여야 합니다: " + discountRate);
        }
        this.discountRate = discountRate;
    }

    @Override
    public int apply(int price) {
        return (int) (price * (1 - discountRate));
    }

    @Override
    public String describe() {
        return "비율 할인 (" + (int)(discountRate * 100) + "%)";
    }
}
