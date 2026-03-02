package org.example.chapter04_2;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CompositionMain {

    // ----------------------------------------------------------------
    // 합성 기반 안전한 스택 — java.util.Stack의 상속 안티패턴 대체
    // ----------------------------------------------------------------
    static class SafeStack<T> {
        private final ArrayList<T> storage = new ArrayList<>();

        public void push(T item) {
            storage.add(item);
        }

        public T pop() {
            if (storage.isEmpty()) throw new NoSuchElementException("스택이 비어 있습니다");
            return storage.remove(storage.size() - 1);
        }

        public T peek() {
            if (storage.isEmpty()) throw new NoSuchElementException("스택이 비어 있습니다");
            return storage.get(storage.size() - 1);
        }

        public boolean isEmpty() { return storage.isEmpty(); }
        public int size()        { return storage.size();    }

        @Override
        public String toString() { return storage.toString(); }
    }

    public static void main(String[] args) {
        demonstrateComposition();
        demonstrateAggregation();
        demonstrateStackAntipattern();
        demonstrateSafeStack();
    }

    // ----------------------------------------------------------------
    // 1. 합성 — Car + Engine 생명주기 종속
    // ----------------------------------------------------------------
    static void demonstrateComposition() {
        System.out.println("=== 합성 (Composition) ===");

        var car = new Car("제네시스 G80", 3500);
        System.out.println(car);

        car.start();
        System.out.println("주행 중: " + car.isRunning());

        car.stop();
        System.out.println("정지 중: " + !car.isRunning());
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. 집약 — Department + Employee 독립 생존
    // ----------------------------------------------------------------
    static void demonstrateAggregation() {
        System.out.println("=== 집약 (Aggregation) ===");

        // Employee는 독립적으로 생성
        var e1 = new Employee("E001", "홍길동", "개발자");
        var e2 = new Employee("E002", "이몽룡", "디자이너");
        var e3 = new Employee("E003", "성춘향", "기획자");

        var dev = new Department("개발팀");
        var design = new Department("디자인팀");

        dev.addEmployee(e1);
        dev.addEmployee(e3);
        design.addEmployee(e2);

        dev.printRoster();
        design.printRoster();

        // 부서 이동 — Employee는 여전히 존재
        dev.removeEmployee(e3);
        design.addEmployee(e3);

        System.out.println("\n-- 이동 후 --");
        dev.printRoster();
        design.printRoster();

        // Department 없이도 Employee는 독립 존재
        System.out.println("\ne1 직접 접근: " + e1);
        e1.promote("시니어 개발자");
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. 상속 오용 — java.util.Stack 안티패턴 시연
    // ----------------------------------------------------------------
    static void demonstrateStackAntipattern() {
        System.out.println("=== 상속 오용 — Stack extends Vector 안티패턴 ===");

        var stack = new java.util.Stack<String>();
        stack.push("first");
        stack.push("second");
        stack.push("third");

        // Vector 메서드로 LIFO 불변식 파괴
        stack.add(0, "injected at bottom");  // Vector.add(index, element)
        System.out.println("LIFO 불변식 파괴된 스택: " + stack);
        System.out.println("pop: " + stack.pop());  // "third" — 예상대로
        System.out.println("최하단에 임의 원소 삽입 가능 — 안티패턴");
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. 합성 기반 안전한 스택
    // ----------------------------------------------------------------
    static void demonstrateSafeStack() {
        System.out.println("=== 합성 기반 SafeStack ===");

        var stack = new SafeStack<String>();
        stack.push("first");
        stack.push("second");
        stack.push("third");

        System.out.println("스택: " + stack);
        System.out.println("peek: " + stack.peek());
        System.out.println("pop: " + stack.pop());
        System.out.println("pop: " + stack.pop());
        System.out.println("남은 스택: " + stack);

        try {
            new SafeStack<>().pop();
        } catch (NoSuchElementException e) {
            System.out.println("빈 스택 pop: " + e.getMessage());
        }
    }
}
