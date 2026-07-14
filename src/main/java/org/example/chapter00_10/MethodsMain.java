package org.example.chapter00_10;

public class MethodsMain {
    public static void main(String[] args) {
        // 1. 반환값이 있는 메서드 / void 메서드
        int sum = add(3, 5);
        System.out.println("add(3, 5) = " + sum);
        printGreeting("홍길동");

        // 2. 매개변수를 활용한 계산
        System.out.println("multiply(3, 4) = " + multiply(3, 4));

        // 3. 메서드 오버로딩 - 타입/개수가 다른 add
        System.out.println("add(int, int) = " + add(1, 2));
        System.out.println("add(double, double) = " + add(1.5, 2.5));
        System.out.println("add(int, int, int) = " + add(1, 2, 3));

        // 4. 중복 코드를 메서드로 추출하여 재사용
        System.out.println("average(10, 20) = " + average(10, 20));
        System.out.println("average(30, 40) = " + average(30, 40));
    }

    // 반환값이 있는 메서드
    public static int add(int a, int b) {
        return a + b;
    }

    // 반환값이 없는 메서드 (void)
    public static void printGreeting(String name) {
        System.out.println("안녕하세요, " + name);
    }

    public static int multiply(int x, int y) {
        return x * y;
    }

    // 오버로딩: 매개변수 타입이 다름
    public static double add(double a, double b) {
        return a + b;
    }

    // 오버로딩: 매개변수 개수가 다름
    public static int add(int a, int b, int c) {
        return a + b + c;
    }

    public static double average(int a, int b) {
        return (a + b) / 2.0;
    }
}
