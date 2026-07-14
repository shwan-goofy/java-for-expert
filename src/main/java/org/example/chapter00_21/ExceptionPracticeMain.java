package org.example.chapter00_21;

public class ExceptionPracticeMain {
    public static void main(String[] args) {
        // 1. 커스텀 예외와 부가 정보(getShortage())
        BankAccount account = new BankAccount(1000);
        try {
            account.withdraw(3000);
        } catch (InsufficientBalanceException e) {
            System.out.println("예외 메시지: " + e.getMessage());
            System.out.println("부족한 금액: " + e.getShortage());
        }

        // 2. 예외 전환 - getCause()로 원본 예외 확인
        try {
            account.parseAge("스물다섯");
        } catch (InvalidInputException e) {
            System.out.println("예외 메시지: " + e.getMessage());
            System.out.println("원인(cause): " + e.getCause());
        }

        // 3. try-with-resources - 자원의 자동 해제
        try (SimpleResource resource = new SimpleResource("파일1")) {
            resource.use();
        }
        // close()가 자동으로 호출되어 위 출력 다음에 "파일1 자원 닫기"가 이어진다

        // 예외가 발생해도 close()는 반드시 호출된다
        try (SimpleResource resource = new SimpleResource("파일2")) {
            throw new RuntimeException("작업 중 오류 발생");
        } catch (RuntimeException e) {
            System.out.println("예외 처리: " + e.getMessage());
        }

        // 4. 잘못된 예외 처리 vs 올바른 예외 처리
        System.out.println("--- 잘못된 예: catch를 비워두면 문제가 조용히 사라진다 ---");
        try {
            account.withdraw(999999);
        } catch (InsufficientBalanceException e) {
            // 나쁜 예: 아무 처리도 하지 않고 무시 (실제 코드에서는 지양)
        }
        System.out.println("위 예외는 조용히 무시되어 원인 파악이 어렵다");

        System.out.println("--- 올바른 예: 최소한 로그를 남긴다 ---");
        try {
            account.withdraw(999999);
        } catch (InsufficientBalanceException e) {
            System.out.println("[로그] 출금 실패: " + e.getMessage() + " (부족액: " + e.getShortage() + ")");
        }
    }
}
