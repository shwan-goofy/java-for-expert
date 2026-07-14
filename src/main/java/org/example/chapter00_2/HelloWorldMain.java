package org.example.chapter00_2;

public class HelloWorldMain {
    public static void main(String[] args) {
        // 1. println vs print
        System.out.println("Hello, World!");

        System.out.print("A");
        System.out.print("B");
        System.out.println(); // 줄바꿈만 출력
        System.out.println("A");
        System.out.println("B");

        // 2. 문자열과 숫자를 함께 출력 (+ 연결)
        String name = "홍길동";
        int age = 20;
        System.out.println("이름: " + name + ", 나이: " + age);

        // 3. 주석 사용 예시
        // 한 줄 주석: 아래 줄은 실행 결과에 영향을 주지 않는다
        /*
         * 여러 줄 주석
         * 코드에 대한 긴 설명을 작성할 때 사용한다
         */
        System.out.println("주석은 실행되지 않는다");
    }
}
