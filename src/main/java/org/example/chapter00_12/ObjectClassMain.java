package org.example.chapter00_12;

import java.util.HashSet;
import java.util.Set;

public class ObjectClassMain {
    public static void main(String[] args) {
        // 1. == 와 equals() 의 차이 (재정의 전: RawPoint)
        RawPoint raw1 = new RawPoint(1, 2);
        RawPoint raw2 = new RawPoint(1, 2);
        System.out.println("--- 재정의 전 (RawPoint) ---");
        System.out.println("raw1 == raw2: " + (raw1 == raw2));           // false, 주소 비교
        System.out.println("raw1.equals(raw2): " + raw1.equals(raw2));   // false, 재정의 안 함 -> ==와 동일
        System.out.println("raw1.toString(): " + raw1);                  // 클래스명@해시코드 형태

        // 2. equals/hashCode/toString 재정의 후 (Point)
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        System.out.println("--- 재정의 후 (Point) ---");
        System.out.println("p1 == p2: " + (p1 == p2));           // false, 주소는 여전히 다름
        System.out.println("p1.equals(p2): " + p1.equals(p2));   // true, 필드 값이 같으므로 동등
        System.out.println("p1.toString(): " + p1);              // Point{x=1, y=2}

        // 3. HashSet에서 equals/hashCode 재정의의 중요성
        Set<RawPoint> rawSet = new HashSet<>();
        rawSet.add(new RawPoint(1, 2));
        rawSet.add(new RawPoint(1, 2)); // 값은 같지만 equals/hashCode 미재정의 -> 중복으로 취급되어 둘 다 추가됨
        System.out.println("RawPoint HashSet 크기: " + rawSet.size()); // 2

        Set<Point> pointSet = new HashSet<>();
        pointSet.add(new Point(1, 2));
        pointSet.add(new Point(1, 2)); // 값이 같으므로 중복 제거됨
        System.out.println("Point HashSet 크기: " + pointSet.size()); // 1

        // 4. getClass() 로 런타임 타입 확인
        System.out.println("p1.getClass().getName(): " + p1.getClass().getName());
        System.out.println("p1.getClass().getSimpleName(): " + p1.getClass().getSimpleName());
    }
}
