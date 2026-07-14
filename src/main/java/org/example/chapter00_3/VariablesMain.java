package org.example.chapter00_3;

public class VariablesMain {
    public static void main(String[] args) {
        // 1. 8가지 기본형(primitive type)
        byte b = 100;
        short s = 1000;
        int i = 100_000;             // 숫자 구분자 _ 는 가독성을 위해 사용 가능
        long l = 10_000_000_000L;    // int 범위를 넘으므로 L 필수
        float f = 3.14f;             // f 접미사 필수
        double d = 3.14;
        char c = 'A';
        boolean flag = true;

        System.out.println("byte: " + b);
        System.out.println("short: " + s);
        System.out.println("int: " + i);
        System.out.println("long: " + l);
        System.out.println("float: " + f);
        System.out.println("double: " + d);
        System.out.println("char: " + c);
        System.out.println("boolean: " + flag);

        // 2. 참조형(reference type) — String, 배열
        String name = "홍길동";       // String은 기본형이 아니라 클래스(참조형)
        int[] numbers = {1, 2, 3};   // 배열도 참조형
        System.out.println("이름: " + name);
        System.out.println("배열 첫 번째 값: " + numbers[0]);

        // 3. final 상수
        final double PI = 3.14159;
        System.out.println("PI: " + PI);
        // PI = 3.14; // 컴파일 에러 — final 변수는 재할당 불가

        // 4. 정수 오버플로 확인
        byte maxByte = 127;
        byte overflowed = (byte) (maxByte + 1); // 범위를 넘으면 -128로 순환됨
        System.out.println("byte 최댓값 + 1 = " + overflowed);

        int maxInt = Integer.MAX_VALUE;
        System.out.println("int 최댓값: " + maxInt);
        System.out.println("int 최댓값 + 1 = " + (maxInt + 1)); // 오버플로 발생 → 음수로 순환
    }
}
