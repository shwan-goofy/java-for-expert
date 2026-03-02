package org.example.chapter05_1;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * SRP 적용 — OrderRepository는 주문 저장/조회만 담당한다.
 */
public class OrderRepository {

    private final Map<String, Order> store = new HashMap<>();

    public void save(Order order) {
        store.put(order.getOrderId(), order);
        System.out.println("[Repository] 주문 저장: " + order.getOrderId());
    }

    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(store.get(orderId));
    }

    public int count() { return store.size(); }
}
