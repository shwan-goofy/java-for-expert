package org.example.chapter01_1;

import java.util.ArrayList;
import java.util.List;

/**
 * 장바구니 총액 계산기.
 * 기본형 vs 참조형 파라미터 전달 방식과 final, var 키워드를 보여준다.
 */
public class CartCalculator {

    // final 상수 — 한 번 선언 후 재할당 불가
    private static final double TAX_RATE = 0.1;
    private static final double VIP_DISCOUNT_RATE = 0.15;
    private static final double NORMAL_DISCOUNT_RATE = 0.05;

    /**
     * 기본형 파라미터 전달 예시.
     * price는 복사본이므로 원본에 영향 없음.
     */
    public static int applyTaxToValue(int price) {
        price = (int) (price * (1 + TAX_RATE));  // 복사본 변경
        return price;
    }

    /**
     * 참조형 파라미터 전달 예시.ㅌ
     * cart는 같은 리스트 객체를 가리키므로 add() 호출이 원본에 반영됨.
     */
    public static void addFreeItem(List<String> cart) {
        cart.add("사은품");
    }

    /**
     * 참조 교체 예시.
     * 새 리스트를 할당해도 호출부의 변수는 바뀌지 않음.
     */
    public static void replaceCart(List<String> cart) {
        cart = new ArrayList<>();  // 지역 참조만 교체, 원본 불변
        cart.add("교체된 항목");   // 원본 리스트에 영향 없음
    }

    /**
     * 총액 계산 — 메서드 오버로딩 (int 버전).
     */
    public static int calculateTotal(List<Integer> prices) {
        int total = 0;
        for (int price : prices) {
            total += price;
        }
        return total;
    }

    /**
     * 총액 계산 — 메서드 오버로딩 (double 버전).
     */
    public static double calculateTotal(List<Double> prices, boolean isDouble) {
        double total = 0.0;
        for (double price : prices) {
            total += price;
        }
        return total;
    }

    /**
     * 할인 적용.
     * var 키워드로 지역 변수 타입 추론.
     */
    public static int applyDiscount(int total, boolean isVip) {
        var discountRate = isVip ? VIP_DISCOUNT_RATE : NORMAL_DISCOUNT_RATE;  // double로 추론
        var discountAmount = (int) (total * discountRate);                    // int로 추론
        return total - discountAmount;
    }

    /**
     * 세금 포함 최종 금액 계산.
     */
    public static int calculateFinalPrice(int discountedPrice) {
        var taxAmount = (int) (discountedPrice * TAX_RATE);
        return discountedPrice + taxAmount;
    }
}
