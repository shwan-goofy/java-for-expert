package org.example.chapter06_3;

import java.util.List;

/**
 * Chapter 6-3: 지역 클래스와 익명 클래스 실행 진입점.
 *
 * <p>지역 클래스, 익명 클래스, 람다 전환, this 참조 차이를 순서대로 시연한다.
 */
public class AnonymousMain {

    private final String ownerName = "AnonymousMain";

    // -------------------------------------------------------------------------
    // 지역 클래스 시연
    // -------------------------------------------------------------------------

    private static void demonstrateLocalClass() {
        System.out.println("=== 지역 클래스 ===");

        String prefix = "LOG";  // effectively final — 로컬 클래스가 캡처 가능

        // 메서드 안에 선언된 지역 클래스
        class Formatter {
            private final String separator;

            Formatter(String separator) {
                this.separator = separator;
            }

            String format(String message) {
                return "[" + prefix + separator + "] " + message;  // prefix 캡처
            }
        }

        Formatter formatter = new Formatter("::");
        System.out.println(formatter.format("서버 시작"));
        System.out.println(formatter.format("요청 수신"));

        // prefix = "CHANGED";  // 컴파일 오류 — 캡처 변수는 effectively final
    }

    // -------------------------------------------------------------------------
    // 익명 클래스 시연
    // -------------------------------------------------------------------------

    private static void demonstrateAnonymousClass() {
        System.out.println("\n=== 익명 클래스 ===");

        // 함수형 인터페이스가 아닌 경우 — 익명 클래스 필요 (람다 불가)
        Validator.StringValidator notEmptyValidator = new Validator.StringValidator() {
            @Override
            public boolean validate(String value) {
                return value != null && !value.isBlank();
            }

            @Override
            public String errorMessage() {
                return "값이 비어 있습니다";
            }
        };

        Validator.StringValidator lengthValidator = new Validator.StringValidator() {
            @Override
            public boolean validate(String value) {
                return value != null && value.length() >= 3;
            }

            @Override
            public String errorMessage() {
                return "3자 이상이어야 합니다";
            }
        };

        Validator.validate("", notEmptyValidator);
        Validator.validate("hello", notEmptyValidator);
        Validator.validate("hi", lengthValidator);
        Validator.validate("hello", lengthValidator);
    }

    // -------------------------------------------------------------------------
    // 람다 전환 비교
    // -------------------------------------------------------------------------

    private static void demonstrateLambdaConversion() {
        System.out.println("\n=== 람다 전환 비교 ===");

        // 익명 클래스 — 함수형 인터페이스 구현 (장황)
        Validator.Predicate<Integer> isPositiveAnonymous = new Validator.Predicate<Integer>() {
            @Override
            public boolean test(Integer value) {
                return value > 0;
            }
        };

        // 람다 — 동일 로직, 간결
        Validator.Predicate<Integer> isPositiveLambda = value -> value > 0;

        List<Integer> numbers = List.of(-3, 0, 5, -1, 8);

        System.out.print("익명 클래스 필터 결과: ");
        numbers.stream()
                .filter(n -> Validator.filter(n, isPositiveAnonymous))
                .forEach(n -> System.out.print(n + " "));

        System.out.print("\n람다 필터 결과:       ");
        numbers.stream()
                .filter(n -> Validator.filter(n, isPositiveLambda))
                .forEach(n -> System.out.print(n + " "));

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // this 참조 차이
    // -------------------------------------------------------------------------

    private void demonstrateThisReference() {
        System.out.println("\n=== this 참조 차이 ===");

        // 익명 클래스 안의 this — 익명 클래스 자신을 가리킨다
        Runnable anonymousRunnable = new Runnable() {
            @Override
            public void run() {
                // 여기서 this는 익명 Runnable 인스턴스
                System.out.println("익명 클래스 this: " + this.getClass().getSimpleName());
                // ownerName에 접근하려면 AnonymousMain.this.ownerName 필요
                System.out.println("외부 클래스 접근: " + AnonymousMain.this.ownerName);
            }
        };

        // 람다 안의 this — 바깥 클래스(AnonymousMain)를 가리킨다
        Runnable lambdaRunnable = () -> {
            // 여기서 this는 AnonymousMain 인스턴스
            System.out.println("람다 this: " + this.getClass().getSimpleName());
            System.out.println("람다에서 ownerName: " + ownerName);  // this.ownerName과 동일
        };

        anonymousRunnable.run();
        lambdaRunnable.run();
    }

    public static void main(String[] args) {
        demonstrateLocalClass();
        demonstrateAnonymousClass();
        demonstrateLambdaConversion();

        new AnonymousMain().demonstrateThisReference();

        System.out.println("\n=== SortDemo 실행 ===");
        SortDemo.main(new String[]{});
    }
}
