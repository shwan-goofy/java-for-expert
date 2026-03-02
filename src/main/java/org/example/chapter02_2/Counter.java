package org.example.chapter02_2;

/**
 * 인스턴스 변수 vs static 변수, 초기화 블록 실행 순서 예시.
 */
public class Counter {

    // static 변수 — 모든 Counter 인스턴스가 공유
    private static int totalCount;
    private static final int MAX_COUNT = 100;

    // 인스턴스 변수 — Counter마다 독립
    private int count;
    private final String name;

    // static 초기화 블록 — 클래스 로딩 시 최초 한 번 실행
    static {
        totalCount = 0;
        System.out.println("[static 초기화 블록] totalCount = " + totalCount);
    }

    // 인스턴스 초기화 블록 — 생성자 실행 전 매번 실행
    {
        count = 0;
        System.out.println("[인스턴스 초기화 블록] count = " + count);
    }

    public Counter(String name) {
        this.name = name;
        totalCount++;
        System.out.println("[생성자] " + name + " 생성, totalCount = " + totalCount);
    }

    public void increment() {
        if (count >= MAX_COUNT) {
            throw new IllegalStateException("최대 카운트 초과: " + MAX_COUNT);
        }
        count++;
    }

    public void decrement() {
        if (count <= 0) {
            throw new IllegalStateException("카운트는 0 미만이 될 수 없습니다");
        }
        count--;
    }

    public int getCount() { return count; }
    public String getName() { return name; }

    // static 메서드 — 인스턴스 없이 호출
    public static int getTotalCount() { return totalCount; }

    public static void resetTotal() { totalCount = 0; }

    @Override
    public String toString() {
        return "Counter{name='" + name + "', count=" + count + "}";
    }
}
