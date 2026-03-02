package org.example.chapter02_2;

public class FieldsMethodsMain {

    public static void main(String[] args) {
        demonstrateInitOrder();
        demonstrateStaticVsInstance();
        demonstrateStaticFactory();
    }

    // ----------------------------------------------------------------
    // 1. 초기화 순서
    // ----------------------------------------------------------------
    static void demonstrateInitOrder() {
        System.out.println("=== 초기화 순서 ===");
        System.out.println("-- 첫 번째 Counter 생성 --");
        var c1 = new Counter("카운터A");

        System.out.println("\n-- 두 번째 Counter 생성 --");
        var c2 = new Counter("카운터B");

        System.out.println("\n총 생성 수 (static): " + Counter.getTotalCount());
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. static 변수 vs 인스턴스 변수
    // ----------------------------------------------------------------
    static void demonstrateStaticVsInstance() {
        System.out.println("=== static 변수 vs 인스턴스 변수 ===");

        var c1 = new Counter("A");
        var c2 = new Counter("B");

        c1.increment();
        c1.increment();
        c1.increment();
        c2.increment();

        System.out.println(c1 + " (인스턴스 count: " + c1.getCount() + ")");
        System.out.println(c2 + " (인스턴스 count: " + c2.getCount() + ")");
        System.out.println("공유 totalCount: " + Counter.getTotalCount());

        // static 메서드는 클래스명으로 호출
        Counter.resetTotal();
        System.out.println("리셋 후 totalCount: " + Counter.getTotalCount());
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. 정적 팩토리 메서드
    // ----------------------------------------------------------------
    static void demonstrateStaticFactory() {
        System.out.println("=== 정적 팩토리 메서드 ===");

        var user    = MemberFactory.ofUser("홍길동", "hong@example.com");
        var admin   = MemberFactory.ofAdmin("이몽룡", "lee@example.com");
        var manager = MemberFactory.ofManager("성춘향", "sung@example.com");
        var inactive = MemberFactory.ofInactive("임꺽정", "lim@example.com",
                MemberFactory.Role.USER);

        System.out.println(user);
        System.out.println(admin);
        System.out.println(manager);
        System.out.println(inactive);

        // 비활성 → 활성 전환 (불변 객체 스타일 — 새 인스턴스 반환)
        var activated = inactive.activate();
        System.out.println("활성화 후: " + activated);

        // static 유틸리티 메서드
        System.out.println("유효한 이메일?: " + MemberFactory.isValidEmail("test@example.com"));
        System.out.println("잘못된 이메일?: " + MemberFactory.isValidEmail("notanemail"));
    }
}
