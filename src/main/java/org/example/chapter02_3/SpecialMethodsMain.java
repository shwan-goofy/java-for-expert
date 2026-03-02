package org.example.chapter02_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SpecialMethodsMain {

    public static void main(String[] args) {
        demonstrateToString();
        demonstrateEqualsHashCode();
        demonstrateHashMapContract();
        demonstrateComparable();
        demonstrateComparator();
        demonstrateLombok();
    }

    // ----------------------------------------------------------------
    // 1. toString
    // ----------------------------------------------------------------
    static void demonstrateToString() {
        System.out.println("=== toString ===");

        var v = new Vector2D(3.0, 4.0);
        System.out.println(v);  // toString() 자동 호출
        System.out.println("문자열 연결: " + v);  // 동일

        var v2 = new Vector2D(1.0, 1.0);
        System.out.println(v.add(v2));
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. equals / hashCode
    // ----------------------------------------------------------------
    static void demonstrateEqualsHashCode() {
        System.out.println("=== equals / hashCode ===");

        var v1 = new Vector2D(3.0, 4.0);
        var v2 = new Vector2D(3.0, 4.0);
        var v3 = new Vector2D(1.0, 1.0);

        System.out.println("v1 == v2 (참조): " + (v1 == v2));          // false
        System.out.println("v1.equals(v2): "   + v1.equals(v2));       // true
        System.out.println("v1.equals(v3): "   + v1.equals(v3));       // false
        System.out.println("hashCode 같음: "   + (v1.hashCode() == v2.hashCode())); // true
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. HashMap / HashSet에서 equals+hashCode 계약 중요성
    // ----------------------------------------------------------------
    static void demonstrateHashMapContract() {
        System.out.println("=== HashMap / HashSet 계약 ===");

        var set = new HashSet<Vector2D>();
        set.add(new Vector2D(3.0, 4.0));
        set.add(new Vector2D(3.0, 4.0));  // 중복 — equals/hashCode로 판별

        System.out.println("HashSet 크기 (equals+hashCode 재정의): " + set.size());  // 1

        var map = new HashMap<Vector2D, String>();
        map.put(new Vector2D(1.0, 0.0), "단위벡터X");
        System.out.println("키 조회: " + map.get(new Vector2D(1.0, 0.0)));  // 단위벡터X
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. Comparable — Collections.sort()
    // ----------------------------------------------------------------
    static void demonstrateComparable() {
        System.out.println("=== Comparable (자연 순서) ===");

        var vectors = new ArrayList<Vector2D>();
        vectors.add(new Vector2D(3.0, 4.0));  // magnitude = 5.0
        vectors.add(new Vector2D(1.0, 0.0));  // magnitude = 1.0
        vectors.add(new Vector2D(2.0, 2.0));  // magnitude = 2.83

        Collections.sort(vectors);  // compareTo() 사용
        System.out.println("크기 오름차순:");
        vectors.forEach(v -> System.out.println("  " + v));
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 5. Comparator — 다양한 정렬 기준
    // ----------------------------------------------------------------
    static void demonstrateComparator() {
        System.out.println("=== Comparator (외부 정렬 기준) ===");

        var products = List.of(
            new Product("바나나",  1200, "과일"),
            new Product("사과",    3000, "과일"),
            new Product("우유",    2500, "유제품"),
            new Product("치즈",    8000, "유제품"),
            new Product("딸기",    5000, "과일")
        );

        var sorted = new ArrayList<>(products);

        sorted.sort(Product.BY_NAME);
        System.out.println("이름 오름차순: " + sorted.stream().map(Product::getName).toList());

        sorted.sort(Product.BY_PRICE_DESC);
        System.out.println("가격 내림차순: " + sorted.stream().map(p -> p.getName() + "(" + p.getPrice() + ")").toList());

        sorted.sort(Product.BY_CATEGORY_THEN_PRICE);
        System.out.println("카테고리→가격: " + sorted.stream().map(p -> p.getCategory() + "/" + p.getName()).toList());
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 6. Lombok @ToString, @EqualsAndHashCode
    // ----------------------------------------------------------------
    static void demonstrateLombok() {
        System.out.println("=== Lombok @ToString, @EqualsAndHashCode ===");

        var p1 = new Product("사과", 3000, "과일");
        var p2 = new Product("사과", 3000, "과일");
        var p3 = new Product("바나나", 1200, "과일");

        System.out.println("Lombok toString: " + p1);
        System.out.println("equals (같은 값): " + p1.equals(p2));  // true
        System.out.println("equals (다른 값): " + p1.equals(p3)); // false

        var set = new HashSet<Product>();
        set.add(p1);
        set.add(p2);
        System.out.println("HashSet 크기 (Lombok): " + set.size());  // 1
    }
}
