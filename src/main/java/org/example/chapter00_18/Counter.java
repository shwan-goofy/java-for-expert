package org.example.chapter00_18;

public class Counter {
    private int count = 0;

    public int getCount() {
        return count;
    }

    // 인스턴스 내부 클래스 — Counter 인스턴스에 종속되며, private 필드에 직접 접근 가능
    public class Incrementer {
        public void increment() {
            count++;
        }
    }
}
