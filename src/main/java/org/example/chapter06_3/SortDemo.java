package org.example.chapter06_3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Comparator를 익명 클래스 → 람다 → 메서드 참조 순서로 단순화하는 예제.
 *
 * <p>함수형 인터페이스({@link Comparator})에 대한 세 가지 구현 방식을 비교한다.
 */
public class SortDemo {

    record Person(String name, int age) {}

    /** 익명 클래스 방식 — 장황하지만 추상 메서드가 여러 개인 경우에도 동작한다. */
    public static void sortByAgeAnonymous(List<Person> people) {
        people.sort(new Comparator<Person>() {
            @Override
            public int compare(Person a, Person b) {
                return Integer.compare(a.age(), b.age());
            }
        });
    }

    /** 람다 방식 — 함수형 인터페이스에 대한 간결한 표현. */
    public static void sortByAgeLambda(List<Person> people) {
        people.sort((a, b) -> Integer.compare(a.age(), b.age()));
    }

    /** 메서드 참조 방식 — 메서드가 이미 존재한다면 가장 간결하다. */
    public static void sortByAgeMethodRef(List<Person> people) {
        people.sort(Comparator.comparingInt(Person::age));
    }

    /** 이름 길이로 정렬 — 익명 클래스와 람다 비교. */
    public static void sortByNameLength(List<String> names) {
        System.out.println("\n--- 이름 길이 정렬: 익명 클래스 ---");
        List<String> copy1 = new java.util.ArrayList<>(names);
        copy1.sort(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Integer.compare(a.length(), b.length());
            }
        });
        System.out.println(copy1);

        System.out.println("--- 이름 길이 정렬: 람다 ---");
        List<String> copy2 = new java.util.ArrayList<>(names);
        copy2.sort((a, b) -> Integer.compare(a.length(), b.length()));
        System.out.println(copy2);

        System.out.println("--- 이름 길이 정렬: 메서드 참조 ---");
        List<String> copy3 = new java.util.ArrayList<>(names);
        copy3.sort(Comparator.comparingInt(String::length));
        System.out.println(copy3);
    }

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Charlie", 30),
                new Person("Alice", 25),
                new Person("Bob", 35)
        );

        List<Person> copy1 = new java.util.ArrayList<>(people);
        sortByAgeAnonymous(copy1);
        System.out.println("익명 클래스 정렬: " + copy1);

        List<Person> copy2 = new java.util.ArrayList<>(people);
        sortByAgeLambda(copy2);
        System.out.println("람다 정렬:        " + copy2);

        List<Person> copy3 = new java.util.ArrayList<>(people);
        sortByAgeMethodRef(copy3);
        System.out.println("메서드 참조 정렬: " + copy3);

        sortByNameLength(Arrays.asList("Bob", "Alice", "Christopher", "Jo"));
    }
}
