package org.example.chapter01_3;

/**
 * 도메인 커스텀 예외 예시.
 * RuntimeException을 상속해 Unchecked Exception으로 만든다.
 */
public class CustomException {

    /**
     * 잔액 부족 예외.
     * 발생 원인 값(요청 금액, 현재 잔액)을 필드로 보관한다.
     */
    public static class InsufficientBalanceException extends RuntimeException {

        private final int requestedAmount;
        private final int currentBalance;

        public InsufficientBalanceException(int requestedAmount, int currentBalance) {
            super("잔액 부족: 요청=" + requestedAmount + "원, 현재 잔액=" + currentBalance + "원");
            this.requestedAmount = requestedAmount;
            this.currentBalance = currentBalance;
        }

        public int getRequestedAmount() { return requestedAmount; }
        public int getCurrentBalance()  { return currentBalance; }
    }

    /**
     * 유효하지 않은 계좌 상태 예외.
     */
    public static class InvalidAccountStateException extends RuntimeException {

        public InvalidAccountStateException(String state) {
            super("유효하지 않은 계좌 상태: " + state);
        }
    }

    /**
     * 파일 읽기 예외 — Checked IOException을 Unchecked로 래핑.
     * 예외 체이닝: 원인 예외를 cause로 보존한다.
     */
    public static class FileReadException extends RuntimeException {

        private final String filePath;

        public FileReadException(String filePath, Throwable cause) {
            super("파일 읽기 실패: " + filePath, cause);
            this.filePath = filePath;
        }

        public String getFilePath() { return filePath; }
    }
}
