package org.example.chapter02_4;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

public class BuilderMain {

    public static void main(String[] args) {
        demonstrateManualBuilder();
        demonstrateLombokBuilder();
        demonstrateDslQueryBuilder();
        demonstrateLombokConstructors();
    }

    // ----------------------------------------------------------------
    // 1. 직접 구현한 Builder
    // ----------------------------------------------------------------
    static void demonstrateManualBuilder() {
        System.out.println("=== 직접 구현한 Builder ===");

        var person = PersonBuilder.PersonManual.builder()
                .name("홍길동")
                .age(25)
                .email("hong@example.com")
                .role("ADMIN")
                .tags(List.of("java", "spring"))
                .build();

        System.out.println(person);

        // 이름 누락 시 예외
        try {
            PersonBuilder.PersonManual.builder().age(20).build();
        } catch (IllegalStateException e) {
            System.out.println("예외 확인: " + e.getMessage());
        }

        // 기본값 사용
        var minimal = PersonBuilder.PersonManual.builder().name("이몽룡").build();
        System.out.println("기본값 사용: " + minimal);
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. Lombok @Builder
    // ----------------------------------------------------------------
    static void demonstrateLombokBuilder() {
        System.out.println("=== Lombok @Builder ===");

        var person = PersonBuilder.PersonLombok.builder()
                .name("성춘향")
                .age(20)
                .email("sung@example.com")
                .tag("java")       // @Singular — 단수 이름으로 하나씩 추가
                .tag("oop")
                .tag("lombok")
                .build();

        System.out.println(person);  // @ToString 자동 생성

        // @Builder.Default — role이 "USER"로 기본값 설정됨
        var withDefault = PersonBuilder.PersonLombok.builder()
                .name("임꺽정")
                .build();
        System.out.println("기본값 사용: " + withDefault);
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. DSL QueryBuilder
    // ----------------------------------------------------------------
    static void demonstrateDslQueryBuilder() {
        System.out.println("=== DSL QueryBuilder ===");

        // 마치 SQL처럼 읽히는 Java 코드 (Fluent Interface)
        var query1 = QueryBuilder
                .select("name", "email", "age")
                .from("users")
                .where("age > 18")
                .where("active = true")
                .orderBy("name")
                .limit(10)
                .build();

        System.out.println(query1);

        var query2 = QueryBuilder
                .selectAll()
                .from("orders")
                .where("status = 'PAID'")
                .orderByDesc("created_at")
                .limit(20)
                .offset(40)
                .build();

        System.out.println(query2);

        // FROM 누락 시 예외
        try {
            QueryBuilder.select("name").build();
        } catch (IllegalStateException e) {
            System.out.println("예외 확인: " + e.getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. Lombok 생성자 어노테이션 비교
    // ----------------------------------------------------------------
    static void demonstrateLombokConstructors() {
        System.out.println("=== Lombok 생성자 어노테이션 ===");

        // @AllArgsConstructor — 모든 필드 생성자
        var allArgs = new AllArgsExample("홍길동", 25, "hong@example.com");
        System.out.println("@AllArgs: " + allArgs);

        // @NoArgsConstructor — 기본 생성자
        var noArgs = new NoArgsExample();
        System.out.println("@NoArgs: " + noArgs);

        // @RequiredArgsConstructor — final 필드만
        var required = new RequiredArgsExample("상품", 5000);
        System.out.println("@RequiredArgs: " + required);
    }

    @AllArgsConstructor
    @ToString
    static class AllArgsExample {
        private String name;
        private int age;
        private String email;
    }

    @NoArgsConstructor
    @ToString
    static class NoArgsExample {
        private String name = "미설정";
        private int value = 0;
    }

    @RequiredArgsConstructor
    @ToString
    static class RequiredArgsExample {
        private final String name;   // final — 포함
        private final int price;     // final — 포함
        private String description;  // non-final — 제외
    }
}
