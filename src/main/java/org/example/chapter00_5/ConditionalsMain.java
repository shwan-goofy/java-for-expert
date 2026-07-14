package org.example.chapter00_5;

public class ConditionalsMain {
    public static void main(String[] args) {
        // 1. if - else if - else 로 등급 판정
        int score = 85;
        if (score >= 90) {
            System.out.println("등급: A");
        } else if (score >= 80) {
            System.out.println("등급: B");
        } else if (score >= 70) {
            System.out.println("등급: C");
        } else {
            System.out.println("등급: F");
        }

        // 2. 전통 switch 문 - break 로 폴스루 방지
        int day = 3;
        String dayName;
        switch (day) {
            case 1:
                dayName = "월요일";
                break;
            case 2:
                dayName = "화요일";
                break;
            case 3:
                dayName = "수요일";
                break;
            default:
                dayName = "알 수 없음";
                break;
        }
        System.out.println("요일: " + dayName);

        // 3. switch 식 (Java 14+ 화살표 문법)
        int month = 7;
        String season = switch (month) {
            case 12, 1, 2 -> "겨울";
            case 3, 4, 5 -> "봄";
            case 6, 7, 8 -> "여름";
            case 9, 10, 11 -> "가을";
            default -> "알 수 없음";
        };
        System.out.println(month + "월의 계절: " + season);

        // 4. 중첩 조건문
        int age = 25;
        boolean hasLicense = true;
        if (age >= 18) {
            if (hasLicense) {
                System.out.println("운전 가능");
            } else {
                System.out.println("면허가 필요합니다");
            }
        } else {
            System.out.println("나이 제한에 걸립니다");
        }
    }
}
