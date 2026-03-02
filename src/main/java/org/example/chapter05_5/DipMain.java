package org.example.chapter05_5;

public class DipMain {

    // DIP 위반 — 구체 클래스에 직접 의존
    static class ViolationNotificationService {
        private final EmailSender emailSender = new EmailSender();  // 직접 생성

        public void notify(String email, String message) {
            emailSender.send(email, message);
            // SMS로 교체하려면 이 클래스 코드를 수정해야 함 → DIP 위반
        }
    }

    public static void main(String[] args) {
        demonstrateViolation();
        demonstrateDIP();
        demonstrateRuntimeSwitch();
    }

    static void demonstrateViolation() {
        System.out.println("=== DIP 위반 ===");
        var service = new ViolationNotificationService();
        service.notify("hong@example.com", "주문 확인");
        System.out.println("→ EmailSender에 직접 의존 — SMS 교체 시 코드 수정 필요\n");
    }

    static void demonstrateDIP() {
        System.out.println("=== DIP 적용 — 이메일 ===");
        var emailService = new NotificationService(new EmailSender());
        emailService.notifyOrderConfirmation("hong@example.com", "ORD-001");
        emailService.notifyShipping("hong@example.com", "TRK-12345");

        System.out.println("\n=== DIP 적용 — SMS ===");
        var smsService = new NotificationService(new SmsSender());
        smsService.notifyOrderConfirmation("010-1234-5678", "ORD-002");
        smsService.notifyShipping("010-1234-5678", "TRK-67890");
        System.out.println("→ NotificationService 코드 수정 없이 구현체 교체\n");
    }

    static void demonstrateRuntimeSwitch() {
        System.out.println("=== 런타임 구현체 교체 ===");

        // 환경에 따라 구현체를 선택 (예: 환경변수, 설정 파일)
        boolean useEmail = false;

        MessageSender sender = useEmail ? new EmailSender() : new SmsSender();
        var service = new NotificationService(sender);

        service.notifyPromotion(
                useEmail ? "user@example.com" : "010-9999-0000",
                "봄 할인 이벤트 20% OFF");

        // 람다로 테스트용 구현체 주입
        MessageSender testSender = (recipient, message) ->
                System.out.println("[TestSender] " + recipient + " | " + message);

        var testService = new NotificationService(testSender);
        testService.notifyPromotion("test-user", "테스트 메시지");

        System.out.println("→ SOLID 5원칙 학습 완료!");
    }
}
