package org.example.chapter04_3;

/**
 * sealed interface + record 구현체 예시 (JDK 17).
 * permits에 명시된 타입만 이 인터페이스를 구현할 수 있다.
 */
public class Shape {

    public sealed interface GeometricShape
            permits Shape.Circle, Shape.Rectangle, Shape.Triangle {

        double area();
        double perimeter();

        default String describe() {
            return getClass().getSimpleName()
                    + " [넓이=" + String.format("%.2f", area())
                    + ", 둘레=" + String.format("%.2f", perimeter()) + "]";
        }
    }

    public record Circle(double radius) implements GeometricShape {
        public Circle {
            if (radius <= 0) throw new IllegalArgumentException("반지름은 양수여야 합니다");
        }

        @Override
        public double area() { return Math.PI * radius * radius; }

        @Override
        public double perimeter() { return 2 * Math.PI * radius; }
    }

    public record Rectangle(double width, double height) implements GeometricShape {
        public Rectangle {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("너비와 높이는 양수여야 합니다");
            }
        }

        @Override
        public double area() { return width * height; }

        @Override
        public double perimeter() { return 2 * (width + height); }
    }

    public record Triangle(double a, double b, double c) implements GeometricShape {
        public Triangle {
            if (a <= 0 || b <= 0 || c <= 0) {
                throw new IllegalArgumentException("변의 길이는 양수여야 합니다");
            }
            if (a + b <= c || b + c <= a || a + c <= b) {
                throw new IllegalArgumentException("삼각형 성립 조건 불만족");
            }
        }

        @Override
        public double area() {
            double s = perimeter() / 2;
            return Math.sqrt(s * (s - a) * (s - b) * (s - c));
        }

        @Override
        public double perimeter() { return a + b + c; }
    }
}
