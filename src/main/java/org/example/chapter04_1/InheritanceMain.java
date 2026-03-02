package org.example.chapter04_1;

import java.util.List;

public class InheritanceMain {

    public static void main(String[] args) {
        demonstrateBasicInheritance();
        demonstratePolymorphism();
        demonstratePatternMatching();
        demonstrateSwitchPattern();
    }

    // ----------------------------------------------------------------
    // 1. 기본 상속 — super(), @Override
    // ----------------------------------------------------------------
    static void demonstrateBasicInheritance() {
        System.out.println("=== 기본 상속 ===");

        var dog = new Dog("멍멍이", 3, "진돗개");
        var cat = new Cat("야옹이", 5, true);

        System.out.println(dog.speak());
        System.out.println(cat.speak());
        System.out.println(dog.describe());
        System.out.println(cat.describe());

        // final 메서드 — 타입 이름 반환
        System.out.println("종류: " + dog.getSpecies());
        System.out.println("종류: " + cat.getSpecies());

        dog.train();
        dog.fetch();
        cat.loseLife();
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. 다형성 — upcasting, 동적 디스패치
    // ----------------------------------------------------------------
    static void demonstratePolymorphism() {
        System.out.println("=== 다형성 ===");

        // upcasting — Animal 타입으로 Dog/Cat 인스턴스 참조
        List<Animal> animals = List.of(
            new Dog("바둑이", 2, "포메라니안"),
            new Cat("치즈", 4, false),
            new Dog("흰둥이", 1, "말티즈"),
            new Cat("나비", 6, true)
        );

        // 동적 디스패치 — 실제 인스턴스 타입의 speak() 호출
        for (Animal animal : animals) {
            System.out.println(animal.speak());
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. Pattern matching for instanceof (JDK 16+)
    // ----------------------------------------------------------------
    static void demonstratePatternMatching() {
        System.out.println("=== Pattern matching for instanceof (JDK 16+) ===");

        List<Animal> animals = List.of(
            new Dog("멍멍이", 3, "진돗개"),
            new Cat("야옹이", 5, true),
            new Dog("바둑이", 2, "포메라니안")
        );

        for (Animal animal : animals) {
            // 기존: instanceof + 명시적 캐스팅
            // if (animal instanceof Dog) { Dog dog = (Dog) animal; dog.fetch(); }

            // Pattern matching (JDK 16+): 한 번에 타입 확인 + 바인딩
            if (animal instanceof Dog dog) {
                System.out.println(dog.getName() + " → fetch! (품종: " + dog.getBreed() + ")");
            } else if (animal instanceof Cat cat) {
                System.out.println(cat.getName() + " → 야옹~ (실내: " + cat.isIndoor() + ")");
            }
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. Switch expression + type pattern (JDK 17)
    // ----------------------------------------------------------------
    static void demonstrateSwitchPattern() {
        System.out.println("=== switch expression + type pattern ===");

        List<Animal> animals = List.of(
            new Dog("멍멍이", 3, "진돗개"),
            new Cat("야옹이", 5, true),
            new Dog("흰둥이", 1, "말티즈")
        );

        for (Animal animal : animals) {
            String info = switch (animal) {
                case Dog d -> "개: " + d.getName() + " (" + d.getBreed() + ")"
                        + (d.isTrained() ? " [훈련됨]" : "");
                case Cat c -> "고양이: " + c.getName()
                        + " [생명: " + c.getLives() + "]";
                default    -> "기타: " + animal.getName();
            };
            System.out.println(info);
        }
    }
}
