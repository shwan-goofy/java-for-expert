package org.example.chapter01_2;

/**
 * 제네릭 클래스 기본 예시.
 * T는 타입 파라미터로, 인스턴스 생성 시 실제 타입으로 대체된다.
 */
public class Box<T> { // new Box<Integer>, new Box<String> ...

    private T value;

    public Box(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    @Override
    public String toString() {
        return "Box[" + value + "]";
    }

    /**
     * 바운디드 타입 파라미터 예시.
     * T는 Comparable을 구현한 타입만 허용한다.
     */
    public static class SortedPair<T extends Comparable<T>> {

        private final T smaller;
        private final T larger;

        public SortedPair(T a, T b) {
            if (a.compareTo(b) <= 0) {
                this.smaller = a;
                this.larger = b;
            } else {
                this.smaller = b;
                this.larger = a;
            }
        }

        public T getSmaller() { return smaller; }
        public T getLarger()  { return larger;  }

        @Override
        public String toString() {
            return "SortedPair[" + smaller + ", " + larger + "]";
        }
    }
}
