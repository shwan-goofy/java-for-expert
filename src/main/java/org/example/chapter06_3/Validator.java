package org.example.chapter06_3;

/**
 * 두 종류의 검증 인터페이스를 통해 익명 클래스와 람다의 적용 조건을 보여준다.
 */
public class Validator {

    /**
     * 추상 메서드가 두 개인 인터페이스.
     *
     * <p>함수형 인터페이스가 아니므로 람다로 구현할 수 없다.
     * 익명 클래스로만 즉석 구현이 가능하다.
     */
    public interface StringValidator {
        boolean validate(String value);
        String errorMessage();
    }

    /**
     * 추상 메서드가 하나인 함수형 인터페이스.
     *
     * <p>람다 표현식으로 구현 가능하다.
     * 익명 클래스와 람다 두 방식 모두 사용할 수 있다.
     */
    @FunctionalInterface
    public interface Predicate<T> {
        boolean test(T value);
    }

    /**
     * StringValidator를 적용해 값을 검증하고 결과를 출력한다.
     * 메서드가 두 개이므로 람다 대신 익명 클래스나 구현 클래스가 필요하다.
     */
    public static void validate(String value, StringValidator validator) {
        if (validator.validate(value)) {
            System.out.println("'" + value + "' → 유효");
        } else {
            System.out.println("'" + value + "' → 무효: " + validator.errorMessage());
        }
    }

    /**
     * Predicate를 적용해 값을 필터링한다.
     * 함수형 인터페이스이므로 람다로 전달할 수 있다.
     */
    public static <T> boolean filter(T value, Predicate<T> predicate) {
        return predicate.test(value);
    }
}
