package org.example.chapter03_1;

public class AccessControlMain {

    public static void main(String[] args) {
        demonstrateAccessLevels();
        demonstrateEncapsulation();
        demonstratePackagePrivate();
    }

    // ----------------------------------------------------------------
    // 1. 접근 수준 확인
    // ----------------------------------------------------------------
    static void demonstrateAccessLevels() {
        System.out.println("=== 접근 제어자 ===");

        var account = new BankAccount("1234-5678", "홍길동", 100000);

        // public — 외부 접근 가능
        System.out.println("잔액 (public getter): " + account.getBalance());
        System.out.println("계좌번호 (public getter): " + account.getAccountNumber());

        // protected — 같은 패키지에서 접근 가능
        System.out.println("소유자 (protected): " + account.ownerName);
        account.ownerName = "이몽룡";  // 같은 패키지에서 직접 수정 가능
        System.out.println("소유자 변경 후: " + account.ownerName);

        // package-private — 같은 패키지에서 접근 가능
        System.out.println("거래 횟수 (default): " + account.transactionCount);

        // private — 접근 불가 (컴파일 에러)
        // account.balance  // 컴파일 에러
        // account.frozen   // 컴파일 에러
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. 캡슐화 — private 필드를 통한 유효성 강제
    // ----------------------------------------------------------------
    static void demonstrateEncapsulation() {
        System.out.println("=== 캡슐화 ===");

        var account = new BankAccount("9876-5432", "성춘향", 50000);
        System.out.println(account);

        // 정상 입금
        account.deposit(30000);
        System.out.println("입금 후: " + account.getBalance());

        // 유효하지 않은 입금 — private 필드를 직접 수정할 수 없으므로 검증이 강제됨
        try {
            account.deposit(-5000);
        } catch (IllegalArgumentException e) {
            System.out.println("유효성 검사: " + e.getMessage());
        }

        // 잔액 부족
        try {
            account.withdraw(200000);
        } catch (IllegalStateException e) {
            System.out.println("잔액 부족: " + e.getMessage());
        }

        // 계좌 동결
        account.freeze();
        try {
            account.deposit(10000);
        } catch (IllegalStateException e) {
            System.out.println("동결 계좌: " + e.getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. package-private 활용
    // ----------------------------------------------------------------
    static void demonstratePackagePrivate() {
        System.out.println("=== package-private ===");

        var account = new BankAccount("1111-2222", "임꺽정", 0);

        // 같은 패키지에서 직접 접근 (실무에서는 지양, 테스트 코드 등에서 활용)
        account.transactionCount = 10;  // package-private 직접 접근
        System.out.println("거래 횟수 (직접 설정): " + account.transactionCount);

        // 입출금 후 자동 증가
        account.deposit(1000);
        account.deposit(2000);
        System.out.println("거래 후 횟수: " + account.transactionCount);
    }
}
