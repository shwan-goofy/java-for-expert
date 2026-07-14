package org.example.chapter00_13;

// 가변 객체 예제 — setter로 내부 상태를 자유롭게 바꿀 수 있다
public class MutableMoney {
    private int amount;

    public MutableMoney(int amount) {
        this.amount = amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
