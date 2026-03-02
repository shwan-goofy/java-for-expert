package org.example.chapter04_3;

/**
 * abstract class와 interface 비교.
 */
public class Payment {

    // ----------------------------------------------------------------
    // abstract class — 공통 상태(필드)와 공통 구현을 공유
    // ----------------------------------------------------------------
    public abstract static class AbstractPayment {

        protected final String paymentId;
        protected final String ownerName;

        public AbstractPayment(String paymentId, String ownerName) {
            this.paymentId = paymentId;
            this.ownerName = ownerName;
        }

        // 공통 구현 — 모든 결제에 공통으로 적용
        public void validate(int amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("결제 금액은 양수여야 합니다: " + amount);
            }
        }

        // 추상 메서드 — 서브클래스가 반드시 구현
        public abstract boolean pay(int amount);

        public abstract String getPaymentType();

        public String getPaymentId() { return paymentId; }
        public String getOwnerName() { return ownerName; }
    }

    // ----------------------------------------------------------------
    // interface — 능력/계약 정의 (상태 없음)
    // ----------------------------------------------------------------
    public interface Refundable {
        boolean refund(int amount);

        // default 메서드 (JDK 8+) — 인터페이스에 기본 구현 제공
        default boolean partialRefund(int amount, int percentage) {
            int refundAmount = (int) (amount * percentage / 100.0);
            return refund(refundAmount);
        }

        // static 메서드 (JDK 8+) — 팩토리 또는 유틸리티
        static Refundable noRefund() {
            return amount -> {
                System.out.println("환불 불가 정책");
                return false;
            };
        }
    }

    // ----------------------------------------------------------------
    // @FunctionalInterface — 단일 추상 메서드, 람다로 구현 가능
    // ----------------------------------------------------------------
    @FunctionalInterface
    public interface DiscountStrategy {
        int apply(int price);

        // default 메서드는 함수형 인터페이스에 추가 가능 (추상 메서드는 여전히 하나)
        default DiscountStrategy andThen(DiscountStrategy next) {
            return price -> next.apply(this.apply(price));
        }
    }
}
