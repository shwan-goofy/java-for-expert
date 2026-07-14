package org.example.chapter00_21;

public class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        if (amount > balance) {
            int shortage = amount - balance;
            throw new InsufficientBalanceException("잔액이 부족합니다", shortage);
        }
        balance -= amount;
    }

    // 저수준 예외(NumberFormatException)를 의미 있는 예외로 전환
    public int parseAge(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("나이는 숫자여야 합니다: " + input, e);
        }
    }
}
