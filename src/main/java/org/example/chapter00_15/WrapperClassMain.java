package org.example.chapter00_15;

import java.util.ArrayList;
import java.util.List;

public class WrapperClassMain {
    public static void main(String[] args) throws ClassNotFoundException {
        // 1. 오토박싱 / 언박싱
        int primitive = 10;
        Integer boxed = primitive;   // 오토박싱
        int unboxed = boxed;         // 언박싱
        System.out.println("boxed: " + boxed + ", unboxed: " + unboxed);

        List<Integer> list = new ArrayList<>();
        list.add(5);                 // 오토박싱
        int value = list.get(0);     // 언박싱
        System.out.println("list.get(0): " + value);

        // 2. Integer 캐싱 범위(-128~127)에 따른 == 비교
        Integer a = 100;
        Integer b = 100;
        System.out.println("100 == 100 (캐시 범위): " + (a == b)); // true

        Integer c = 200;
        Integer d = 200;
        System.out.println("200 == 200 (캐시 범위 밖): " + (c == d)); // false
        System.out.println("200.equals(200): " + c.equals(d));       // true — 항상 이 방법을 사용

        // 3. null 언박싱 시 NullPointerException
        Integer count = null;
        try {
            int result = count + 1; // 언박싱 시도 -> NPE
            System.out.println(result);
        } catch (NullPointerException e) {
            System.out.println("NullPointerException 발생: null을 언박싱하려고 했다");
        }

        // 4. Class 클래스로 런타임 타입 정보 얻기
        Class<?> c1 = "hello".getClass();
        Class<?> c2 = String.class;
        Class<?> c3 = Class.forName("java.lang.String");

        System.out.println("c1 == c2: " + (c1 == c2)); // true — 같은 클래스는 단 하나의 Class 객체
        System.out.println("c1 == c3: " + (c1 == c3)); // true

        System.out.println("getName(): " + c1.getName());
        System.out.println("getSimpleName(): " + c1.getSimpleName());
        System.out.println("isInterface(): " + c1.isInterface());
    }
}
