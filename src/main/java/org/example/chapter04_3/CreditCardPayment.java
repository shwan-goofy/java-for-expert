package org.example.chapter04_3;

/**
 * abstract class 상속 + interface 구현 예시.
 */
public class CreditCardPayment extends Payment.AbstractPayment
        implements Payment.Refundable {

    private final String cardNumber;
    private int balance;

    public CreditCardPayment(String paymentId, String ownerName,
                             String cardNumber, int balance) {
        super(paymentId, ownerName);
        this.cardNumber = cardNumber;
        this.balance    = balance;
    }

    @Override
    public boolean pay(int amount) {
        validate(amount);  // 부모의 공통 검증
        if (amount > balance) {
            System.out.println("카드 한도 초과: 요청=" + amount + ", 한도=" + balance);
            return false;
        }
        balance -= amount;
        System.out.println("신용카드 결제 성공: " + amount + "원 (남은 한도: " + balance + "원)");
        return true;
    }

    @Override
    public boolean refund(int amount) {
        balance += amount;
        System.out.println("카드 환불: " + amount + "원 (현재 한도: " + balance + "원)");
        return true;
    }

    @Override
    public String getPaymentType() {
        return "CREDIT_CARD";
    }

    public String getMaskedCardNumber() {
        return cardNumber.substring(0, 4) + "-****-****-"
                + cardNumber.substring(cardNumber.length() - 4);
    }
}
