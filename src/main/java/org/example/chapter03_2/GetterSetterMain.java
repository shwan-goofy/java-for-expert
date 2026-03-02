package org.example.chapter03_2;

public class GetterSetterMain {

    // ----------------------------------------------------------------
    // sealed class 예시 (JDK 17)
    // ----------------------------------------------------------------
    sealed interface Shape permits GetterSetterMain.Circle, GetterSetterMain.Rect {}

    record Circle(double radius) implements Shape {}
    record Rect(double width, double height) implements Shape {}

    public static void main(String[] args) {
        demonstrateTraditionalGetterSetter();
        demonstrateRecord();
        demonstrateLombok();
        demonstrateSealedClass();
    }

    // ----------------------------------------------------------------
    // 1. 전통적인 getter/setter
    // ----------------------------------------------------------------
    static void demonstrateTraditionalGetterSetter() {
        System.out.println("=== 전통적인 getter/setter ===");

        var t = new Temperature(100.0);
        System.out.println(t);
        System.out.println("끓는점 여부: " + t.isBoiling());

        t.setCelsius(0.0);
        System.out.println("변경 후: " + t);
        System.out.println("어는점 여부: " + t.isFreezing());

        // 화씨로 설정
        t.setFahrenheit(98.6);
        System.out.println("화씨 98.6°F 설정 후: " + t);

        // 유효성 검사 확인
        try {
            t.setCelsius(-300.0);
        } catch (IllegalArgumentException e) {
            System.out.println("예외: " + e.getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. record accessor
    // ----------------------------------------------------------------
    static void demonstrateRecord() {
        System.out.println("=== record accessor ===");

        var t1 = TemperatureRecord.Temperature.ofCelsius(100.0);
        var t2 = TemperatureRecord.Temperature.ofFahrenheit(32.0);
        var t3 = TemperatureRecord.Temperature.ofKelvin(300.0);

        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);

        // accessor — get 없이 필드명으로 접근
        System.out.println("celsius accessor: " + t1.celsius());
        System.out.println("fahrenheit(): " + t1.fahrenheit());

        // record 불변 — celsius 수정 불가
        // t1.celsius = 50;  // 컴파일 에러

        // 동등성
        var t4 = TemperatureRecord.Temperature.ofCelsius(100.0);
        System.out.println("값 동등: " + t1.equals(t4));
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. Lombok
    // ----------------------------------------------------------------
    static void demonstrateLombok() {
        System.out.println("=== Lombok ===");

        var selective = new MemberLombok.MemberSelective("USR-001", "홍길동", 25, "secret");
        System.out.println(selective);

        selective.setName("이몽룡");
        selective.setAge(30);
        // selective.setId("NEW-ID");  // 컴파일 에러 — Setter(AccessLevel.NONE)

        System.out.println("변경 후: " + selective);
        // selective.getPassword();  // 컴파일 에러 — Getter(AccessLevel.NONE)

        // @Data → @RequiredArgsConstructor 생성: final 필드(id)만 생성자에 포함
        var data = new MemberLombok.MemberData("USR-002");
        data.setName("성춘향");
        data.setAge(20);
        data.setEmail("ch@example.com");
        System.out.println(data);

        data.setName("변경된 이름");
        // data.setId("NEW");  // 컴파일 에러 — final 필드는 setter 없음
        System.out.println("변경 후: " + data);
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. sealed class + switch expression (JDK 17)
    // ----------------------------------------------------------------
    static void demonstrateSealedClass() {
        System.out.println("=== sealed class (JDK 17) ===");

        Shape[] shapes = {
            new Circle(5.0),
            new Rect(4.0, 6.0),
            new Circle(3.0)
        };

        for (Shape shape : shapes) {
            // 모든 허용 서브타입을 처리하지 않으면 컴파일 에러
            double area = switch (shape) {
                case Circle c -> Math.PI * c.radius() * c.radius();
                case Rect r   -> r.width() * r.height();
            };
            System.out.printf("%s → 넓이: %.2f%n", shape, area);
        }
    }
}
