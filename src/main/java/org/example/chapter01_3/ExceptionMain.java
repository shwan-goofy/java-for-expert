package org.example.chapter01_3;

import java.io.IOException;

public class ExceptionMain {

    public static void main(String[] args) {
        demonstrateCheckedUnchecked();
        demonstrateTryCatchFinally();
        demonstrateTryWithResources();
        demonstrateCustomException();
        demonstrateSwitchThrow();
        demonstrateMultiCatch();
    }

    // ----------------------------------------------------------------
    // 1. Checked vs Unchecked
    // ----------------------------------------------------------------
    static void demonstrateCheckedUnchecked() {
        System.out.println("=== Checked vs Unchecked ===");

        // Unchecked — 처리 강제 없음
        try {
            validateAmount(-100);
        } catch (IllegalArgumentException e) {
            System.out.println("Unchecked 처리: " + e.getMessage());
        }

        // Checked — 처리하지 않으면 컴파일 에러
        try {
            CheckedExample.queryDatabaseChecked(null, "SELECT 1");
        } catch (IOException e) {
            System.out.println("Checked 처리: " + e.getMessage());
        }
        System.out.println();
    }

    static void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("금액은 양수여야 합니다: " + amount);
        }
    }

    // ----------------------------------------------------------------
    // 2. try-catch-finally
    // ----------------------------------------------------------------
    static void demonstrateTryCatchFinally() {
        System.out.println("=== try-catch-finally ===");

        System.out.println("정상 케이스:");
        processPayment(50000, 30000);

        System.out.println("\n잔액 부족 케이스:");
        processPayment(10000, 30000);
        System.out.println();
    }

    static void processPayment(int balance, int amount) {
        try {
            if (amount > balance) {
                throw new CustomException.InsufficientBalanceException(amount, balance);
            }
            System.out.println("결제 성공: " + amount + "원");
        } catch (CustomException.InsufficientBalanceException e) {
            System.out.println("결제 실패: " + e.getMessage());
            System.out.println("  요청: " + e.getRequestedAmount() + "원");
            System.out.println("  잔액: " + e.getCurrentBalance() + "원");
        } finally {
            System.out.println("결제 처리 완료 (finally)");
        }
    }

    // ----------------------------------------------------------------
    // 3. try-with-resources
    // ----------------------------------------------------------------
    static void demonstrateTryWithResources() {
        System.out.println("=== try-with-resources ===");

        // 정상 연결
        String result = CheckedExample.queryDatabase("jdbc:h2:mem:test", "SELECT * FROM users");
        System.out.println(result);

        // 잘못된 URL — FileReadException으로 변환
        try {
            CheckedExample.queryDatabase("", "SELECT 1");
        } catch (CustomException.FileReadException e) {
            System.out.println("래핑된 예외: " + e.getMessage());
            System.out.println("원인 예외:   " + e.getCause().getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. 커스텀 예외 + 예외 체이닝
    // ----------------------------------------------------------------
    static void demonstrateCustomException() {
        System.out.println("=== 커스텀 예외 ===");

        try {
            throw new CustomException.InvalidAccountStateException("FROZEN");
        } catch (CustomException.InvalidAccountStateException e) {
            System.out.println("커스텀 예외 처리: " + e.getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 5. switch expression에서 throw (JDK 14+)
    // ----------------------------------------------------------------
    static void demonstrateSwitchThrow() {
        System.out.println("=== switch expression에서 throw ===");

        for (String status : new String[]{"ACTIVE", "INACTIVE", "UNKNOWN"}) {
            try {
                String label = switch (status) {
                    case "ACTIVE"   -> "활성";
                    case "INACTIVE" -> "비활성";
                    default -> throw new IllegalArgumentException("알 수 없는 상태: " + status);
                };
                System.out.println(status + " → " + label);
            } catch (IllegalArgumentException e) {
                System.out.println("예외: " + e.getMessage());
            }
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 6. multi-catch
    // ----------------------------------------------------------------
    static void demonstrateMultiCatch() {
        System.out.println("=== multi-catch ===");

        for (int i = 0; i < 3; i++) {
            try {
                riskyOperation(i);
            } catch (IllegalArgumentException | IllegalStateException e) {
                // 두 예외를 하나의 catch 블록에서 처리
                System.out.println("multi-catch 처리 [" + i + "]: " + e.getMessage());
            }
        }
    }

    static void riskyOperation(int code) {
        switch (code) {
            case 0 -> System.out.println("정상 처리: code=" + code);
            case 1 -> throw new IllegalArgumentException("잘못된 인자: code=" + code);
            case 2 -> throw new IllegalStateException("잘못된 상태: code=" + code);
        }
    }
}
