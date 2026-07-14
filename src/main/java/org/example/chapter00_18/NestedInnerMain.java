package org.example.chapter00_18;

public class NestedInnerMain {
    public static void main(String[] args) {
        // 1. static 중첩 클래스 — 바깥 인스턴스 없이 바로 생성
        HttpRequest request = new HttpRequest.Builder()
                .url("https://example.com")
                .method("POST")
                .build();
        System.out.println("static 중첩 클래스 결과: " + request);

        // 2. 인스턴스 내부 클래스 — 바깥 인스턴스가 반드시 필요
        Counter counter = new Counter();
        Counter.Incrementer incrementer = counter.new Incrementer(); // outer.new Inner()
        incrementer.increment();
        incrementer.increment();
        incrementer.increment();
        System.out.println("인스턴스 내부 클래스로 증가시킨 count: " + counter.getCount());
    }
}
