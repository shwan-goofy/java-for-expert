package org.example.chapter00_13;

// 불변 객체 예제 — final 클래스, final 필드, setter 없음
public final class Money {
    private final int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    // 값을 변경하는 것처럼 보이지만 실제로는 새 객체를 반환한다
    public Money add(int value) {
        return new Money(this.amount + value);
    }

    @Override
    public String toString() {
        return "Money{amount=" + amount + "}";
    }
}
