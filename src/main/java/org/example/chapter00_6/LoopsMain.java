package org.example.chapter00_6;

public class LoopsMain {
    public static void main(String[] args) {
        // 1. for문
        System.out.println("--- for문: 1~5 출력 ---");
        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }

        // 2. while vs do-while (조건이 처음부터 거짓인 경우)
        System.out.println("--- while (조건이 처음부터 거짓) ---");
        int count = 10;
        while (count < 3) {
            System.out.println("이 줄은 실행되지 않는다: " + count);
        }
        System.out.println("while 종료 후에도 count는 그대로: " + count);

        System.out.println("--- do-while (조건이 처음부터 거짓이어도 1번은 실행) ---");
        int num = 10;
        do {
            System.out.println("num = " + num + " (최소 1회 실행 보장)");
            num++;
        } while (num < 5);

        // 3. 향상된 for문 (enhanced for)
        System.out.println("--- 향상된 for문으로 배열 순회 ---");
        int[] numbers = {10, 20, 30};
        for (int number : numbers) {
            System.out.println(number);
        }

        // 4. break와 continue
        System.out.println("--- break / continue ---");
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                break; // i가 5가 되면 반복 종료
            }
            if (i % 2 == 0) {
                continue; // 짝수는 건너뛰기
            }
            System.out.println("홀수: " + i);
        }

        // 5. 중첩 for문 - 구구단 2~3단
        System.out.println("--- 중첩 for문: 구구단 2~3단 ---");
        for (int dan = 2; dan <= 3; dan++) {
            for (int j = 1; j <= 9; j++) {
                System.out.println(dan + " x " + j + " = " + (dan * j));
            }
        }
    }
}
