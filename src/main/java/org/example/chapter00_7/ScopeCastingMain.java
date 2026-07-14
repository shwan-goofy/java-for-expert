package org.example.chapter00_7;

public class ScopeCastingMain {
    public static void main(String[] args) {
        // 1. 블록 스코프
        int x = 10;
        if (x > 5) {
            int y = 20; // y는 이 if 블록 안에서만 유효
            System.out.println("if 블록 안: x + y = " + (x + y));
        }
        // System.out.println(y); // 컴파일 에러 — y는 스코프를 벗어남

        for (int i = 0; i < 3; i++) {
            int z = i * 2; // z는 for문 블록 안에서만 유효
            System.out.println("for 블록 안: z = " + z);
        }
        // System.out.println(i); // 컴파일 에러 — i도 for문 스코프에 속함

        // 2. 암시적 형변환 (작은 타입 -> 큰 타입, 자동)
        int i2 = 100;
        long l = i2;     // int -> long
        double d = l;    // long -> double
        System.out.println("암시적 형변환: int(" + i2 + ") -> long(" + l + ") -> double(" + d + ")");

        // 3. 명시적 형변환 (큰 타입 -> 작은 타입, 데이터 손실 가능)
        double pi = 3.99;
        int truncated = (int) pi; // 소수점 버림 -> 3 (반올림 아님)
        System.out.println("(int) 3.99 = " + truncated);

        int big = 300;
        byte overflowed = (byte) big; // byte 범위(-128~127)를 넘어서 예상치 못한 값
        System.out.println("(byte) 300 = " + overflowed);

        // 4. 연산 중 자동 승격 (byte, short -> int로 승격)
        byte b1 = 10, b2 = 20;
        int sum = b1 + b2; // byte + byte 연산 결과는 int로 승격됨
        System.out.println("byte + byte 결과 타입은 int로 승격: " + sum);

        int intValue = 10;
        double doubleValue = 3.0;
        double result = intValue + doubleValue; // int가 double로 변환되어 계산
        System.out.println("int + double = " + result);
    }
}
