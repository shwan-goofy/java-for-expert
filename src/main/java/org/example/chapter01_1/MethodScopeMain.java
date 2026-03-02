package org.example.chapter01_1;

import java.util.ArrayList;
import java.util.List;

public class MethodScopeMain {

    // static 변수 — 인스턴스 없이 클래스 차원에서 공유
    static int instanceCount = 0;

    // 인스턴스 변수 — 인스턴스마다 독립적으로 유지
    String name;
    int score;

    public MethodScopeMain(String name, int score) {
        this.name = name;
        this.score = score;
        instanceCount++;
    }

    public static void main(String[] args) {
        demonstratePrimitiveTransfer();
        demonstrateReferenceTransfer();
        demonstrateFinalKeyword();
        demonstrateVarKeyword();
        demonstrateOverloading();
        demonstrateCartScenario();
    }

    // ----------------------------------------------------------------
    // 1. 기본형 파라미터 전달 — 값 복사
    // ----------------------------------------------------------------
    static void demonstratePrimitiveTransfer() {
        System.out.println("=== 기본형 파라미터 전달 ===");
        int price = 10000;
        System.out.println("전달 전: " + price);

        int taxApplied = CartCalculator.applyTaxToValue(price);
        System.out.println("전달 후 원본: " + price);       // 10000 불변
        System.out.println("반환된 값:  " + taxApplied);    // 11000
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. 참조형 파라미터 전달 — 주소 복사
    // ----------------------------------------------------------------
    static void demonstrateReferenceTransfer() {
        System.out.println("=== 참조형 파라미터 전달 ===");

        var cart = new ArrayList<String>();
        cart.add("노트북");
        cart.add("마우스");
        System.out.println("addFreeItem 전: " + cart);

        CartCalculator.addFreeItem(cart);
        System.out.println("addFreeItem 후: " + cart);  // 사은품 추가됨

        CartCalculator.replaceCart(cart);
        System.out.println("replaceCart 후: " + cart);  // 원본 불변
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. final 키워드
    // ----------------------------------------------------------------
    static void demonstrateFinalKeyword() {
        System.out.println("=== final 키워드 ===");

        final int MAX_RETRY = 3;
        System.out.println("최대 재시도 횟수: " + MAX_RETRY);

        // MAX_RETRY = 5;  // 컴파일 에러 — 재할당 불가

        final List<String> tags = new ArrayList<>();
        tags.add("java");   // 내부 수정 가능
        tags.add("oop");
        System.out.println("태그 목록: " + tags);

        // tags = new ArrayList<>();  // 컴파일 에러 — 참조 교체 불가
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. var 키워드 (JDK 10+)
    // ----------------------------------------------------------------
    static void demonstrateVarKeyword() {
        System.out.println("=== var 키워드 ===");

        var message = "Hello, Java 17";       // String으로 추론
        var count = 42;                        // int로 추론
        var ratio = 3.14;                      // double로 추론
        var items = new ArrayList<String>();   // ArrayList<String>으로 추론

        items.add("첫 번째");
        items.add("두 번째");

        System.out.println("message 타입: " + ((Object) message).getClass().getSimpleName());
        System.out.println("count 타입:   " + ((Object) count).getClass().getSimpleName());
        System.out.println("items: " + items);
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 5. 메서드 오버로딩
    // ----------------------------------------------------------------
    static void demonstrateOverloading() {
        System.out.println("=== 메서드 오버로딩 ===");

        var intPrices = List.of(1000, 2000, 3000);
        var doublePrices = List.of(1000.5, 2000.5, 3000.5);

        int totalInt = CartCalculator.calculateTotal(intPrices);
        System.out.println("정수 합계: " + totalInt);

        double totalDouble = CartCalculator.calculateTotal(doublePrices, true);
        System.out.println("실수 합계: " + totalDouble);
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 6. 장바구니 시나리오: static / 인스턴스 / 지역 변수 스코프
    // ----------------------------------------------------------------
    static void demonstrateCartScenario() {
        System.out.println("=== 장바구니 시나리오 ===");

        var intPrices = new ArrayList<Integer>();
        intPrices.add(50000);
        intPrices.add(30000);
        intPrices.add(20000);

        int total = CartCalculator.calculateTotal(intPrices);
        int discounted = CartCalculator.applyDiscount(total, true);  // VIP 할인
        int finalPrice = CartCalculator.calculateFinalPrice(discounted);

        System.out.println("원가 합계:    " + total);
        System.out.println("VIP 할인 후:  " + discounted);
        System.out.println("세금 포함:    " + finalPrice);

        // static 변수 확인
        System.out.println("\n--- 인스턴스 생성 및 static 변수 ---");
        var m1 = new MethodScopeMain("홍길동", 90);
        var m2 = new MethodScopeMain("이몽룡", 85);
        System.out.println(m1.name + " 생성됨");
        System.out.println(m2.name + " 생성됨");
        System.out.println("총 생성된 인스턴스 수 (static): " + instanceCount);
    }
}
