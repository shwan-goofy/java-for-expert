package org.example.chapter01_2;

import java.util.ArrayList;
import java.util.List;

/**
 * 제네릭 유틸리티 메서드 모음.
 * PECS 원칙 (Producer Extends, Consumer Super) 적용 예시를 보여준다.
 */
public class GenericUtils {

    /**
     * 제네릭 메서드 — 두 값 중 큰 값 반환.
     * T는 Comparable을 구현해야 한다.
     */
    public static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    /**
     * 제네릭 메서드 — 두 값 중 작은 값 반환.
     */
    public static <T extends Comparable<T>> T min(T a, T b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    /**
     * PECS: Producer Extends — src는 데이터를 제공하는 역할.
     * src에서 꺼내서 dst에 담는다.
     * PECS: Consumer Super — dst는 데이터를 받는 역할.
     */
    public static <T> void copy(List<? extends T> src, List<? super T> dst) {
        for (T item : src) {
            dst.add(item);
        }
    }

    /**
     * Covariance (공변성) — 읽기 전용.
     * List<Integer>, List<Double> 등 Number의 서브타입 리스트 모두 허용.
     */
    public static double sumNumbers(List<? extends Number> numbers) {
        double total = 0;
        for (Number n : numbers) {
            total += n.doubleValue();
        }
        return total;
    }

    /**
     * Contravariance (반공변성) — 쓰기 전용.
     * List<Number>, List<Object> 등 Integer의 슈퍼타입 리스트 모두 허용.
     */
    public static void addIntegers(List<? super Integer> list, int count) {
        for (int i = 1; i <= count; i++) {
            list.add(i);
        }
    }

    /**
     * 제네릭 메서드로 리스트 역순 복사 반환.
     */
    public static <T> List<T> reversed(List<T> list) {
        var result = new ArrayList<T>();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }
}
