package org.example.chapter00_20;

public class ExceptionTheoryMain {
    public static void main(String[] args) {
        // 1. try-catch-finally 기본 흐름
        try {
            int result = 10 / 0; // ArithmeticException 발생
            System.out.println("이 줄은 실행되지 않는다: " + result);
        } catch (ArithmeticException e) {
            System.out.println("예외 처리: " + e.getMessage());
        } finally {
            System.out.println("finally는 항상 실행된다");
        }

        // 2. 언체크 예외(RuntimeException) - throws 선언 없이도 컴파일됨
        try {
            System.out.println(divide(10, 0));
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 인자: " + e.getMessage());
        }

        // 3. 멀티 catch로 여러 예외 타입 한 번에 처리
        catchMultiple(null);
        catchMultiple("");

        // 4. 체크 예외 - 반드시 catch 하거나 throws로 위임해야 함
        try {
            validate(-1);
        } catch (InvalidAgeException e) {
            System.out.println("체크 예외 처리: " + e.getMessage());
        }

        // 5. 예외 정보 확인
        try {
            throw new RuntimeException("문제가 발생했습니다");
        } catch (RuntimeException e) {
            System.out.println("getMessage(): " + e.getMessage());
            System.out.println("getClass().getName(): " + e.getClass().getName());
        }
    }

    public static int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("0으로 나눌 수 없습니다");
        }
        return a / b;
    }

    public static void catchMultiple(String input) {
        try {
            if (input == null) {
                throw new NullPointerException("input이 null입니다");
            }
            if (input.isEmpty()) {
                throw new IllegalArgumentException("input이 비어있습니다");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("멀티 catch 처리: " + e.getMessage());
        }
    }

    public static void validate(int age) throws InvalidAgeException { // throws — 선언
        if (age < 0) {
            throw new InvalidAgeException("나이는 음수일 수 없습니다"); // throw — 실제 발생
        }
    }
}
