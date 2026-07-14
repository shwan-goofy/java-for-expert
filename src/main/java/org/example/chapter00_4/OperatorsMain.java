package org.example.chapter00_4;

public class OperatorsMain {
    public static void main(String[] args) {
        // 1. 산술 연산자
        int a = 7, b = 2;
        System.out.println("a + b = " + (a + b));
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b = " + (a * b));
        System.out.println("a / b = " + (a / b));       // 정수 나눗셈 -> 3
        System.out.println("a % b = " + (a % b));       // 나머지 -> 1

        double realResult = (double) a / b;              // 형변환 후 실수 나눗셈
        System.out.println("(double) a / b = " + realResult);

        // 2. 비교 연산자 & 논리 연산자 (단축 평가)
        int[] arr = null;
        if (arr != null && arr.length > 0) {
            System.out.println("배열에 값이 있습니다");
        } else {
            System.out.println("배열이 null이거나 비어 있습니다 (NullPointerException 없이 안전하게 처리)");
        }

        boolean isAdult = (20 >= 19) && true;
        System.out.println("isAdult: " + isAdult);

        // 3. 대입 연산자
        int x = 10;
        x += 5; // 15
        x -= 3; // 12
        x *= 2; // 24
        x /= 4; // 6
        System.out.println("최종 x: " + x);

        // 4. 전위/후위 증감 연산자
        int i = 5;
        int postfix = i++; // 대입 후 증가
        int j = 5;
        int prefix = ++j;  // 증가 후 대입
        System.out.println("postfix(i++) 대입 결과: " + postfix + ", 이후 i: " + i);
        System.out.println("prefix(++j) 대입 결과: " + prefix + ", 이후 j: " + j);

        // 5. 삼항 연산자로 등급 판정
        int score = 85;
        String grade = (score >= 90) ? "A" : (score >= 80) ? "B" : "C";
        System.out.println("점수 " + score + "점 -> 등급: " + grade);

        // 6. 연산자 우선순위
        int result = 2 + 3 * 4;      // 14
        int result2 = (2 + 3) * 4;   // 20
        System.out.println("2 + 3 * 4 = " + result);
        System.out.println("(2 + 3) * 4 = " + result2);
    }
}
