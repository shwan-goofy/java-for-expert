package org.example.chapter00_8;

public class TrainingMain {
    public static void main(String[] args) {
        System.out.println("=== 문제 1: FizzBuzz ===");
        problem1();

        System.out.println("=== 문제 2: 별 찍기 ===");
        problem2();

        System.out.println("=== 문제 3: 최댓값과 최솟값 ===");
        problem3();

        System.out.println("=== 문제 4: 구구단 표 (2차원 배열) ===");
        problem4();

        System.out.println("=== 문제 5: 성적 처리기 ===");
        problem5();
    }

    // 문제 1: FizzBuzz — 15의 배수(3과 5 모두)를 가장 먼저 검사해야 한다
    public static void problem1() {
        for (int i = 1; i <= 30; i++) {
            if (i % 15 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }

    // 문제 2: 별 찍기 — 중첩 for문, 바깥은 줄(행), 안쪽은 별 개수
    public static void problem2() {
        for (int row = 1; row <= 5; row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 1; col <= row; col++) {
                line.append("*");
            }
            System.out.println(line);
        }
    }

    // 문제 3: 최댓값과 최솟값 — 첫 값을 기준으로 비교하며 갱신
    public static void problem3() {
        int[] numbers = {45, 22, 89, 7, 63, 90, 11};

        int max = numbers[0];
        int min = numbers[0];
        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
            if (number < min) {
                min = number;
            }
        }

        System.out.println("최댓값: " + max);
        System.out.println("최솟값: " + min);
    }

    // 문제 4: 구구단 표 — 2단~9단을 2차원 배열에 저장 후 출력
    public static void problem4() {
        int[][] result = new int[8][9]; // [dan-2][j-1]

        for (int dan = 2; dan <= 9; dan++) {
            for (int j = 1; j <= 9; j++) {
                result[dan - 2][j - 1] = dan * j;
            }
        }

        for (int dan = 2; dan <= 9; dan++) {
            for (int j = 1; j <= 9; j++) {
                System.out.print(dan + "x" + j + "=" + result[dan - 2][j - 1] + "  ");
            }
            System.out.println();
        }
    }

    // 문제 5: 성적 처리기 — 평균 계산과 등급 판정을 메서드로 분리
    public static void problem5() {
        int[] scores = {88, 95, 72, 60, 99};

        double average = calculateAverage(scores);
        String grade = getGrade(average);

        System.out.println("평균: " + average);
        System.out.println("등급: " + grade);
    }

    public static double calculateAverage(int[] scores) {
        int total = 0;
        for (int score : scores) {
            total += score;
        }
        return total / (double) scores.length; // double로 나눠 소수점 손실 방지
    }

    public static String getGrade(double average) {
        if (average >= 90) {
            return "A";
        } else if (average >= 80) {
            return "B";
        } else if (average >= 70) {
            return "C";
        } else {
            return "F";
        }
    }
}
