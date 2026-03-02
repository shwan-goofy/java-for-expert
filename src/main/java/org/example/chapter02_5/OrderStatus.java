package org.example.chapter02_5;

import java.util.EnumSet;
import java.util.Set;

/**
 * 추상 메서드와 상태 전이 검증이 포함된 enum.
 * 각 상수가 서로 다른 행위를 구현한다.
 */
public enum OrderStatus {

    PENDING("대기중") {
        @Override
        public String processMessage() {
            return "주문이 접수되어 처리를 기다리는 중입니다";
        }

        @Override
        public Set<OrderStatus> allowedTransitions() {
            return EnumSet.of(CONFIRMED, CANCELLED);
        }
    },

    CONFIRMED("확인됨") {
        @Override
        public String processMessage() {
            return "주문이 확인되어 배송 준비 중입니다";
        }

        @Override
        public Set<OrderStatus> allowedTransitions() {
            return EnumSet.of(SHIPPED, CANCELLED);
        }
    },

    SHIPPED("배송중") {
        @Override
        public String processMessage() {
            return "주문이 발송되어 배송 중입니다";
        }

        @Override
        public Set<OrderStatus> allowedTransitions() {
            return EnumSet.of(DELIVERED);
        }
    },

    DELIVERED("배송완료") {
        @Override
        public String processMessage() {
            return "주문이 성공적으로 배송되었습니다";
        }

        @Override
        public Set<OrderStatus> allowedTransitions() {
            return EnumSet.noneOf(OrderStatus.class);
        }
    },

    CANCELLED("취소됨") {
        @Override
        public String processMessage() {
            return "주문이 취소되었습니다";
        }

        @Override
        public Set<OrderStatus> allowedTransitions() {
            return EnumSet.noneOf(OrderStatus.class);
        }
    };

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() { return label; }

    /** 각 상태별 처리 메시지 — 상수마다 다르게 구현 */
    public abstract String processMessage();

    /** 이 상태에서 전환 가능한 상태 목록 */
    public abstract Set<OrderStatus> allowedTransitions();

    /** 전환 가능 여부 확인 */
    public boolean canTransitionTo(OrderStatus next) {
        return allowedTransitions().contains(next);
    }

    /** 상태 전환 — 불가능하면 예외 */
    public OrderStatus transitionTo(OrderStatus next) {
        if (!canTransitionTo(next)) {
            throw new IllegalStateException(
                "상태 전환 불가: " + this.label + " → " + next.label);
        }
        return next;
    }

    @Override
    public String toString() {
        return label + "(" + name() + ")";
    }
}
