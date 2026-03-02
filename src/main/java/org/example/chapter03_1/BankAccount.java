package org.example.chapter03_1;

/**
 * 접근 제어자 예시 — 은행 계좌.
 * private 필드와 public 메서드로 캡슐화를 구현한다.
 */
public class BankAccount {

    // private — 이 클래스 안에서만 접근 가능
    private final String accountNumber;
    private int balance;
    private boolean frozen;

    // protected — 같은 패키지 또는 서브클래스에서 접근 가능
    protected String ownerName;

    // package-private (default) — 같은 패키지에서만 접근 가능
    int transactionCount;

    public BankAccount(String accountNumber, String ownerName, int initialBalance) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("계좌번호는 비어 있을 수 없습니다");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("초기 잔액은 0 이상이어야 합니다");
        }
        this.accountNumber = accountNumber;
        this.ownerName     = ownerName;
        this.balance       = initialBalance;
        this.frozen        = false;
        this.transactionCount = 0;
    }

    // public — 외부 API
    public void deposit(int amount) {
        checkNotFrozen();
        if (amount <= 0) {
            throw new IllegalArgumentException("입금액은 양수여야 합니다: " + amount);
        }
        balance += amount;
        transactionCount++;
    }

    public void withdraw(int amount) {
        checkNotFrozen();
        if (amount <= 0) {
            throw new IllegalArgumentException("출금액은 양수여야 합니다: " + amount);
        }
        if (amount > balance) {
            throw new IllegalStateException(
                "잔액 부족: 요청=" + amount + ", 잔액=" + balance);
        }
        balance -= amount;
        transactionCount++;
    }

    public int getBalance()        { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public boolean isFrozen()      { return frozen; }

    // 계좌 동결 — public API
    public void freeze()   { this.frozen = true;  }
    public void unfreeze() { this.frozen = false; }

    // private — 내부 유효성 검사 (외부에 노출 불필요)
    private void checkNotFrozen() {
        if (frozen) {
            throw new IllegalStateException("동결된 계좌입니다: " + accountNumber);
        }
    }

    @Override
    public String toString() {
        return "BankAccount{accountNumber='" + accountNumber
                + "', owner='" + ownerName
                + "', balance=" + balance
                + ", frozen=" + frozen + "}";
    }
}
