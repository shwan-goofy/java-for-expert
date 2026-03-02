package org.example.chapter05_2;

/**
 * OCP — 할인 정책 인터페이스.
 * 새 할인 정책은 이 인터페이스를 구현하는 새 클래스로 추가한다.
 * 기존 코드를 수정하지 않아도 된다.
 */
public interface DiscountPolicy {

    int apply(int price);

    default String describe() {
        return getClass().getSimpleName();
    }
}
