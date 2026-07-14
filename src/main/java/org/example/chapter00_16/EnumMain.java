package org.example.chapter00_16;

import java.util.Arrays;

public class EnumMain {
    public static void main(String[] args) {
        // 1. enum 기본 메서드
        Season season = Season.SUMMER;
        System.out.println("name(): " + season.name());
        System.out.println("ordinal(): " + season.ordinal());
        System.out.println("values(): " + Arrays.toString(Season.values()));
        System.out.println("valueOf(\"WINTER\"): " + Season.valueOf("WINTER"));

        // enum과 switch
        String message = switch (season) {
            case SPRING -> "따뜻해요";
            case SUMMER -> "더워요";
            case FALL -> "선선해요";
            case WINTER -> "추워요";
        };
        System.out.println("메시지: " + message);

        // 2. 필드와 생성자를 가진 enum
        System.out.println("--- Planet ---");
        for (Planet planet : Planet.values()) {
            System.out.println(planet + " 표면 중력: " + planet.surfaceGravity());
        }

        // 3. 상수별로 다른 동작을 구현하는 enum
        System.out.println("--- Operation ---");
        System.out.println("PLUS.apply(3, 4) = " + Operation.PLUS.apply(3, 4));
        System.out.println("MINUS.apply(3, 4) = " + Operation.MINUS.apply(3, 4));
        System.out.println("MULTIPLY.apply(3, 4) = " + Operation.MULTIPLY.apply(3, 4));
    }
}
