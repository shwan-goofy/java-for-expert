package org.example.chapter01_2;

import java.util.ArrayList;
import java.util.List;

/**
 * Invariance / Covariance / Contravariance 예시와
 * Type Erasure 동작 원리를 보여준다.
 */
public class WildcardExample {

    // ----------------------------------------------------------------
    // 도메인 클래스
    // ----------------------------------------------------------------

    static class Animal {
        String name;
        Animal(String name) { this.name = name; }
        String speak() { return name + ": ..."; }
    }

    static class Dog extends Animal {
        Dog(String name) { super(name); }
        @Override String speak() { return name + ": 멍멍"; }
    }

    static class Cat extends Animal {
        Cat(String name) { super(name); }
        @Override String speak() { return name + ": 야옹"; }
    }

    // ----------------------------------------------------------------
    // Invariance — 기본 동작
    // ----------------------------------------------------------------

    /**
     * List<Dog>는 List<Animal>이 아니다.
     * 이 메서드는 정확히 List<Animal>만 받는다.
     */
    public static void printAnimals(List<Animal> animals) {
        for (Animal a : animals) {
            System.out.println(a.speak());
        }
    }

    // ----------------------------------------------------------------
    // Covariance — <? extends T> : 읽기 전용
    // ----------------------------------------------------------------

    /**
     * List<Dog>, List<Cat>, List<Animal> 모두 받을 수 있다.
     * 꺼낼 때는 Animal 타입으로 취급.
     * 추가는 null 외에 불가 (어떤 서브타입인지 컴파일러가 알 수 없음).
     */
    public static void printAllAnimals(List<? extends Animal> animals) {
        for (Animal a : animals) {
            System.out.println(a.speak());
        }
        // animals.add(new Dog("추가")); // 컴파일 에러
    }

    // ----------------------------------------------------------------
    // Contravariance — <? super T> : 쓰기 전용
    // ----------------------------------------------------------------

    /**
     * List<Dog>, List<Animal>, List<Object> 모두 받을 수 있다.
     * Dog 인스턴스를 안전하게 추가할 수 있다.
     * 꺼낼 때는 Object로만 취급된다.
     */
    public static void fillWithDogs(List<? super Dog> list) {
        list.add(new Dog("멍멍이"));
        list.add(new Dog("바둑이"));
        // Cat 추가 불가 — Dog의 슈퍼타입 리스트에 Cat은 안전하지 않음
        // list.add(new Cat("야옹이")); // 컴파일 에러
    }

    // ----------------------------------------------------------------
    // Type Erasure 시뮬레이션
    // ----------------------------------------------------------------

    /**
     * 컴파일 전: List<String> 파라미터
     * 바이트코드: List 파라미터 (타입 정보 소거)
     * 컴파일러가 자동으로 (String) 캐스팅 코드를 삽입한다.
     */
    public static void typeErasureDemo() {
        List<String> strings = new ArrayList<>();
        strings.add("첫 번째");
        strings.add("두 번째");

        // 런타임에는 List<String>과 List<Integer>가 동일한 클래스
        List<Integer> integers = new ArrayList<>();
        System.out.println("같은 클래스?: " + (strings.getClass() == integers.getClass()));

        // instanceof에 제네릭 파라미터 사용 불가
        // if (strings instanceof List<String>) {}  // 컴파일 에러

        if (strings instanceof List<?>) {
            System.out.println("List<?> instanceof 가능");
        }
    }

    /**
     * Raw Type 사용 시 Heap Pollution 경고 및 위험 시연.
     * 실제 프로덕션 코드에서 Raw Type 사용 금지.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void rawTypeWarningDemo() {
        List<String> strings = new ArrayList<>();
        strings.add("안전한 문자열");

        List rawList = strings;  // unchecked assignment 경고
        rawList.add(9999);       // unchecked call 경고 — Heap Pollution 발생

        try {
            String s = strings.get(1);  // 런타임 ClassCastException
        } catch (ClassCastException e) {
            System.out.println("ClassCastException 발생 (Heap Pollution): " + e.getMessage());
        }
    }
}
