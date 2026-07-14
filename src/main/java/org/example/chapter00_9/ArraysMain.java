package org.example.chapter00_9;

import java.util.Arrays;

public class ArraysMain {
    public static void main(String[] args) {
        // 1. 배열 선언, 생성, 초기화
        int[] numbers = new int[5]; // 기본값 0으로 채워진 크기 5 배열
        int[] scores = new int[]{90, 85, 100};
        int[] ages = {20, 25, 30}; // 축약형

        System.out.println("numbers: " + Arrays.toString(numbers));
        System.out.println("scores: " + Arrays.toString(scores));
        System.out.println("ages: " + Arrays.toString(ages));

        // 2. 배열 순회 - 일반 for문 vs 향상된 for문
        System.out.println("--- 일반 for문 ---");
        for (int i = 0; i < scores.length; i++) {
            System.out.println("scores[" + i + "] = " + scores[i]);
        }

        System.out.println("--- 향상된 for문 ---");
        for (int score : scores) {
            System.out.println(score);
        }

        // 3. 인덱스 범위 초과 예외 상황
        try {
            System.out.println(scores[3]); // scores는 인덱스 0~2까지만 유효
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("예외 발생: " + e.getMessage());
        }

        // 4. 2차원 배열
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6}
        };
        System.out.println("--- 2차원 배열 순회 ---");
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }

        // 5. Arrays 유틸리티 - 정렬과 출력
        int[] unsorted = {5, 3, 1, 4, 2};
        Arrays.sort(unsorted);
        System.out.println("정렬 후: " + Arrays.toString(unsorted));

        int[] copy = Arrays.copyOf(unsorted, unsorted.length);
        System.out.println("복사본: " + Arrays.toString(copy));
    }
}
