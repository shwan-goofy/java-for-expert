package org.example.chapter02_1;

public class ClassInstanceMain {

    public static void main(String[] args) {
        demonstrateConstructorChaining();
        demonstrateReferenceSemantics();
        demonstrateRecord();
        demonstrateRecordValidation();
    }

    // ----------------------------------------------------------------
    // 1. 생성자 체이닝 — this() 호출 순서 확인
    // ----------------------------------------------------------------
    static void demonstrateConstructorChaining() {
        System.out.println("=== 생성자 체이닝 (this()) ===");

        var p1 = new Person("홍길동");
        System.out.println("이름만: " + p1);

        var p2 = new Person("이몽룡", 25);
        System.out.println("이름+나이: " + p2);

        var p3 = new Person("성춘향", 20, "ch@example.com");
        System.out.println("이름+나이+이메일: " + p3);

        var p4 = new Person("임꺽정", 35, "lim@example.com", "ADMIN");
        System.out.println("전체: " + p4);

        // 유효성 검사 확인
        try {
            new Person("", 25);
        } catch (IllegalArgumentException e) {
            System.out.println("예외 확인: " + e.getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. 참조 의미론 — new는 힙에 객체 생성, 변수는 참조 저장
    // ----------------------------------------------------------------
    static void demonstrateReferenceSemantics() {
        System.out.println("=== 참조 의미론 ===");

        var original = new Person("홍길동", 30, "hong@example.com", "USER");
        var reference = original;  // 참조 복사 — 같은 객체

        System.out.println("original == reference? " + (original == reference));

        var other = new Person("홍길동", 30, "hong@example.com", "USER");
        System.out.println("original == other?     " + (original == other));  // false
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. record 기본 사용법
    // ----------------------------------------------------------------
    static void demonstrateRecord() {
        System.out.println("=== record ===");

        var p = new PersonRecord.SimplePoint(3, 4);
        System.out.println("SimplePoint: " + p);
        System.out.println("x accessor: " + p.x());  // get 없이 필드명으로 접근
        System.out.println("y accessor: " + p.y());

        // record는 값 기반 동등성 — equals()가 자동 생성됨
        var p1 = new PersonRecord.SimplePoint(3, 4);
        var p2 = new PersonRecord.SimplePoint(3, 4);
        System.out.println("값 동등: " + p1.equals(p2));  // true

        var member = new PersonRecord.MemberInfo("김철수", 25, "kim@example.com");
        System.out.println("\nMemberInfo: " + member);
        System.out.println("성인 여부: " + member.isAdult());
        System.out.println("마스킹 이메일: " + member.maskedEmail());
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. compact constructor 유효성 검사
    // ----------------------------------------------------------------
    static void demonstrateRecordValidation() {
        System.out.println("=== record compact constructor 유효성 검사 ===");

        var valid = new PersonRecord.PositivePoint(5, 10);
        System.out.println("유효한 좌표: " + valid);

        try {
            new PersonRecord.PositivePoint(-1, 5);
        } catch (IllegalArgumentException e) {
            System.out.println("예외 확인: " + e.getMessage());
        }

        try {
            new PersonRecord.MemberInfo("  ", 25, "test@test.com");
        } catch (IllegalArgumentException e) {
            System.out.println("예외 확인: " + e.getMessage());
        }
    }
}
