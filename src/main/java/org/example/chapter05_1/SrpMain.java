package org.example.chapter05_1;

public class SrpMain {

    // ----------------------------------------------------------------
    // SRP 위반 — 책임이 한 클래스에 집중
    // ----------------------------------------------------------------
    static class OrderViolation {
        private String orderId;
        private String customerEmail;
        private int totalPrice;

        OrderViolation(String orderId, String customerEmail, int totalPrice) {
            this.orderId       = orderId;
            this.customerEmail = customerEmail;
            this.totalPrice    = totalPrice;
        }

        // 주문 데이터 처리
        void addItem(String item) { System.out.println("아이템 추가: " + item); }

        // DB 저장 — 저장 방식이 바뀌면 이 클래스를 수정해야 함
        void saveToDatabase() {
            System.out.println("[위반] DB 저장: " + orderId);
        }

        // 이메일 발송 — 이메일 서비스가 바뀌면 이 클래스를 수정해야 함
        void sendConfirmEmail() {
            System.out.println("[위반] 이메일 발송 → " + customerEmail);
        }
    }

    public static void main(String[] args) {
        demonstrateViolation();
        demonstrateSRP();
    }

    static void demonstrateViolation() {
        System.out.println("=== SRP 위반 ===");
        var order = new OrderViolation("ORD-001", "hong@example.com", 50000);
        order.addItem("노트북");
        order.saveToDatabase();
        order.sendConfirmEmail();
        System.out.println("→ 하나의 클래스에 3가지 변경 이유 존재\n");
    }

    static void demonstrateSRP() {
        System.out.println("=== SRP 적용 ===");

        var order = new Order("ORD-002", "lee@example.com");
        order.addItem("마우스", 30000);
        order.addItem("키보드", 80000);

        var repository = new OrderRepository();
        var notifier   = new EmailNotifier();

        repository.save(order);
        notifier.sendOrderConfirmation(order);

        System.out.println("\n저장된 주문 수: " + repository.count());
        System.out.println("조회: " + repository.findById("ORD-002").orElse(null));
        System.out.println("→ 각 클래스는 하나의 변경 이유만 가짐");
    }
}
