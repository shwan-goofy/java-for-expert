package org.example.chapter00_19;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LocalAnonymousMain {
    public static void main(String[] args) {
        // 1. 지역 클래스
        processOrders(List.of(1000, 2000, 3000));

        // 2. 익명 클래스로 인터페이스 즉석 구현
        Validator notEmpty = new Validator() {
            @Override
            public boolean isValid(String input) {
                return input != null && !input.isEmpty();
            }
        };
        System.out.println("notEmpty.isValid(\"hello\"): " + notEmpty.isValid("hello"));
        System.out.println("notEmpty.isValid(\"\"): " + notEmpty.isValid(""));

        // 3. 지역 변수 캡처 (effectively final)
        Runnable task = createTask("리포트 생성");
        task.run();

        // 4. 익명 클래스로 Comparator 즉석 구현 - 문자열 길이 순 정렬
        List<String> names = new ArrayList<>(List.of("Charlie", "Alice", "Bob"));
        names.sort(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.length() - b.length();
            }
        });
        System.out.println("길이 순 정렬 결과: " + names);
    }

    public static void processOrders(List<Integer> amounts) {
        int discountRate = 10; // effectively final

        class DiscountCalculator { // 지역 클래스 — 이 메서드 안에서만 존재
            int apply(int amount) {
                return amount - (amount * discountRate / 100);
            }
        }

        DiscountCalculator calculator = new DiscountCalculator();
        for (int amount : amounts) {
            System.out.println(amount + " -> 할인 적용가: " + calculator.apply(amount));
        }
    }

    public static Runnable createTask(String name) {
        // name은 재할당되지 않으므로 effectively final -> 익명 클래스가 캡처 가능
        return new Runnable() {
            @Override
            public void run() {
                System.out.println(name + " 작업 실행");
            }
        };
    }
}
