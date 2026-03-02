package org.example.chapter05_1;

import java.util.ArrayList;
import java.util.List;

/**
 * SRP 적용 — Order는 주문 데이터와 비즈니스 로직만 담당한다.
 * 저장(Repository), 알림(Notifier)은 별도 클래스로 분리한다.
 */
public class Order {

    private final String orderId;
    private final String customerEmail;
    private final List<String> items;
    private int totalPrice;

    public Order(String orderId, String customerEmail) {
        this.orderId       = orderId;
        this.customerEmail = customerEmail;
        this.items         = new ArrayList<>();
        this.totalPrice    = 0;
    }

    public void addItem(String itemName, int price) {
        if (price < 0) throw new IllegalArgumentException("가격은 0 이상이어야 합니다");
        items.add(itemName + "(" + price + "원)");
        totalPrice += price;
    }

    public boolean isEmpty() { return items.isEmpty(); }

    public String getOrderId()       { return orderId;       }
    public String getCustomerEmail() { return customerEmail; }
    public List<String> getItems()   { return List.copyOf(items); }
    public int getTotalPrice()       { return totalPrice;    }

    @Override
    public String toString() {
        return "Order{id='" + orderId + "', items=" + items + ", total=" + totalPrice + "}";
    }
}
