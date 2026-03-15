package org.example.chapter06_1;

/**
 * Chapter 6-1: 멤버 내부 클래스 실행 진입점.
 *
 * <p>위반 예시(public 내부 클래스 남용)와 올바른 사용 예시를 비교한다.
 */
public class InnerClassMain {

    // -------------------------------------------------------------------------
    // 위반 예시 — public 내부 클래스 남용
    // -------------------------------------------------------------------------

    /**
     * 나쁜 설계: Item을 public inner class로 노출하면
     * 호출 측이 Order의 내부 구조에 강결합된다.
     */
    static class OrderViolation {
        private String orderId;

        OrderViolation(String orderId) {
            this.orderId = orderId;
        }

        // public inner class — 외부에서 order.new Item() 직접 생성 가능
        public class Item {
            public String name;
            public int price;

            public Item(String name, int price) {
                this.name = name;
                this.price = price;
            }
        }
    }

    // -------------------------------------------------------------------------
    // 시연 메서드
    // -------------------------------------------------------------------------

    private static void demonstrateBasic() {
        System.out.println("=== 기본 내부 클래스 구조 ===");

        Outer outer = new Outer("outerInstance", 10);

        // 방법 1: outer.new Inner() 직접 생성
        Outer.Inner inner1 = outer.new Inner("innerDirect");
        inner1.printNames();

        System.out.println();

        // 방법 2: 외부 클래스의 팩토리 메서드 사용
        Outer.Inner inner2 = outer.createInner("innerFactory");
        inner2.printNames();

        System.out.println();

        // 내부 클래스에서 외부 클래스의 private 필드를 수정
        System.out.println("수정 전: " + outer);
        inner2.incrementOuterValue();
        System.out.println("수정 후: " + outer);
    }

    private static void demonstrateEventButton() {
        System.out.println("\n=== 이벤트 버튼 — 실전 내부 클래스 패턴 ===");

        EventButton button = new EventButton("저장");

        // 같은 버튼에 여러 핸들러 등록
        EventButton.ClickHandler handler1 = button.addClickHandler("FormHandler");
        EventButton.ClickHandler handler2 = button.addClickHandler("LogHandler");

        // 클릭 이벤트 발생 — 두 핸들러 모두 같은 외부 인스턴스(button)의 상태를 공유
        handler1.onClick();
        handler2.onClick();
        handler1.onClick();

        System.out.println("\n버튼 상태: " + button);
        System.out.println("handler1 정보: " + handler1.getButtonInfo());
        System.out.println("handler2 정보: " + handler2.getButtonInfo());

        System.out.println("\n이벤트 로그:");
        button.getEventLog().forEach(log -> System.out.println("  " + log));
    }

    private static void demonstrateViolation() {
        System.out.println("\n=== 위반 예시: public 내부 클래스 ===");

        OrderViolation order = new OrderViolation("ORD-001");

        // 호출 측이 order.new Item() 문법으로 외부 내부 구조에 직접 결합
        OrderViolation.Item item = order.new Item("노트북", 1500000);
        System.out.println("직접 생성된 Item: " + item.name + " / " + item.price + "원");
        System.out.println("→ 호출 측이 Order 내부 구조에 강결합됨");
    }

    public static void main(String[] args) {
        demonstrateBasic();
        demonstrateEventButton();
        demonstrateViolation();
    }
}
