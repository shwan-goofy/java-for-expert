package org.example.chapter06_1;

/**
 * 멤버 내부 클래스(non-static inner class)의 기본 구조를 보여주는 외부 클래스.
 *
 * <p>내부 클래스 {@link Inner}는 Outer 인스턴스 없이 단독으로 생성될 수 없다.
 * 외부 인스턴스에 대한 암묵적 참조를 항상 보유한다.
 */
public class Outer {

    private String name;
    private int value;

    public Outer(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /** 외부 클래스의 name을 반환한다. */
    public String getName() {
        return name;
    }

    /**
     * 멤버 내부 클래스.
     *
     * <p>외부 클래스의 {@code private} 멤버에 직접 접근할 수 있다.
     * 이름 충돌 시 {@code Outer.this.필드명}으로 외부 인스턴스를 명시한다.
     */
    public class Inner {

        private String name;

        public Inner(String name) {
            this.name = name;
        }

        /**
         * 내부 클래스의 name과 외부 클래스의 name을 함께 출력한다.
         * 동일한 이름이 있을 때 Outer.this로 외부 인스턴스를 명시적으로 참조한다.
         */
        public void printNames() {
            System.out.println("Inner name  : " + name);
            System.out.println("Outer name  : " + Outer.this.name);  // 외부 인스턴스 참조
            System.out.println("Outer value : " + value);            // private 필드 직접 접근
        }

        /** 외부 클래스의 value를 수정한다 — private 멤버 쓰기 접근도 가능하다. */
        public void incrementOuterValue() {
            value++;  // 외부 인스턴스의 private 필드 변경
        }
    }

    /**
     * 외부 클래스가 직접 내부 클래스를 생성해 반환하는 팩토리 메서드.
     * 외부에서 {@code outer.new Inner(...)} 형식 없이 사용할 수 있다.
     */
    public Inner createInner(String innerName) {
        return new Inner(innerName);
    }

    @Override
    public String toString() {
        return "Outer{name='" + name + "', value=" + value + "}";
    }
}
