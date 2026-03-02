package org.example.chapter01_2;

import java.util.ArrayList;
import java.util.List;

public class GenericTypeMain {

    public static void main(String[] args) {
        demonstrateBasicGenerics();
        demonstrateBoundedType();
        demonstrateVariance();
        demonstratePECS();
        demonstrateTypeErasure();
    }

    // ----------------------------------------------------------------
    // 1. 기본 제네릭 클래스
    // ----------------------------------------------------------------
    static void demonstrateBasicGenerics() {
        System.out.println("=== 기본 제네릭 클래스 ===");

        var stringBox = new Box<String>("Hello, Generics");
        var intBox    = new Box<Integer>(42);

        System.out.println(stringBox);
        System.out.println(intBox);

        stringBox.set("Updated Value");
        System.out.println("업데이트 후: " + stringBox);

        // var로 제네릭 타입 추론
        var doubleBox = new Box<>(3.14);
        System.out.println("var로 추론된 Box: " + doubleBox);
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. 바운디드 타입 파라미터
    // ----------------------------------------------------------------
    static void demonstrateBoundedType() {
        System.out.println("=== 바운디드 타입 파라미터 ===");

        System.out.println("max(10, 20) = " + GenericUtils.max(10, 20));
        System.out.println("min(10, 20) = " + GenericUtils.min(10, 20));
        System.out.println("max(\"apple\", \"banana\") = " + GenericUtils.max("apple", "banana"));

        var pair = new Box.SortedPair<>(50, 10);
        System.out.println("정렬된 쌍: " + pair);

        var strPair = new Box.SortedPair<>("zebra", "apple");
        System.out.println("정렬된 문자열 쌍: " + strPair);
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. Variance (변성)
    // ----------------------------------------------------------------
    static void demonstrateVariance() {
        System.out.println("=== Variance ===");

        var dogs = new ArrayList<WildcardExample.Dog>();
        dogs.add(new WildcardExample.Dog("멍멍이"));
        dogs.add(new WildcardExample.Dog("바둑이"));

        // Invariance: List<Dog>는 List<Animal>이 아님
        // WildcardExample.printAnimals(dogs); // 컴파일 에러

        // Covariance: <? extends Animal> — List<Dog> 허용
        System.out.println("--- Covariance (? extends Animal) ---");
        WildcardExample.printAllAnimals(dogs);

        var cats = new ArrayList<WildcardExample.Cat>();
        cats.add(new WildcardExample.Cat("야옹이"));
        WildcardExample.printAllAnimals(cats);

        // Contravariance: <? super Dog> — List<Animal> 허용
        System.out.println("--- Contravariance (? super Dog) ---");
        var animals = new ArrayList<WildcardExample.Animal>();
        WildcardExample.fillWithDogs(animals);
        for (var a : animals) {
            System.out.println(a.speak());
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. PECS 원칙
    // ----------------------------------------------------------------
    static void demonstratePECS() {
        System.out.println("=== PECS 원칙 ===");

        var src = List.of(1, 2, 3, 4, 5);
        var dst = new ArrayList<Number>();  // Integer의 슈퍼타입

        GenericUtils.copy(src, dst);  // src: Producer(extends), dst: Consumer(super)
        System.out.println("copy 결과: " + dst);

        var doubles = List.of(1.1, 2.2, 3.3);
        var integers = List.of(10, 20, 30);
        System.out.println("double 합계: " + GenericUtils.sumNumbers(doubles));
        System.out.println("int 합계:    " + GenericUtils.sumNumbers(integers));

        var target = new ArrayList<Number>();
        GenericUtils.addIntegers(target, 3);
        System.out.println("addIntegers 결과: " + target);

        var reversed = GenericUtils.reversed(List.of("a", "b", "c", "d"));
        System.out.println("역순: " + reversed);
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 5. Type Erasure
    // ----------------------------------------------------------------
    static void demonstrateTypeErasure() {
        System.out.println("=== Type Erasure ===");
        WildcardExample.typeErasureDemo();
        WildcardExample.rawTypeWarningDemo();
    }
}
