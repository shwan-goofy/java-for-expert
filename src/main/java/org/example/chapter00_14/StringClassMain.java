package org.example.chapter00_14;

public class StringClassMain {
    public static void main(String[] args) {
        // 1. String의 불변성
        String original = "hello";
        String upper = original.toUpperCase();
        System.out.println("original: " + original); // hello — 원본은 그대로
        System.out.println("upper: " + upper);         // HELLO — 새 문자열

        // 2. 문자열 리터럴과 String Pool
        String a = "hello";
        String b = "hello";
        System.out.println("a == b: " + (a == b)); // true — 같은 String Pool 객체 재사용

        String c = new String("hello");
        System.out.println("a == c: " + (a == c));           // false — c는 힙에 별도로 생성된 객체
        System.out.println("a.equals(c): " + a.equals(c));   // true — 내용은 동일

        // 3. + 연결 vs StringBuilder
        long start1 = System.nanoTime();
        String result = "";
        for (int i = 0; i < 1000; i++) {
            result += i; // 매 반복마다 새로운 String 객체 생성
        }
        long time1 = System.nanoTime() - start1;

        long start2 = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append(i); // 내부 버퍼에 이어붙임
        }
        String result2 = sb.toString();
        long time2 = System.nanoTime() - start2;

        System.out.println("두 결과가 같은가: " + result.equals(result2));
        System.out.println("+ 연산 소요 시간(ns): " + time1);
        System.out.println("StringBuilder 소요 시간(ns): " + time2);

        // StringBuilder 메서드 체이닝
        String message = new StringBuilder()
                .append("이름: ").append("홍길동")
                .append(", 나이: ").append(20)
                .toString();
        System.out.println(message);

        // 4. 자주 쓰는 String 메서드
        String s = "  Hello, Java World!  ";
        System.out.println("length(): " + s.length());
        System.out.println("trim(): [" + s.trim() + "]");
        System.out.println("toUpperCase(): " + s.toUpperCase());
        System.out.println("substring(0,5): " + s.trim().substring(0, 5));
        System.out.println("replace: " + s.trim().replace("Java", "Kotlin"));
        System.out.println("split(', '): " + java.util.Arrays.toString(s.trim().split(", ")));
        System.out.println("contains('Java'): " + s.contains("Java"));
        System.out.println("equalsIgnoreCase: " + s.trim().equalsIgnoreCase("HELLO, JAVA WORLD!"));
    }
}
