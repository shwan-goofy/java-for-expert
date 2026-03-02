package org.example.chapter05_3;

import java.util.List;

public class LspMain {

    // Rectangle을 기대하는 메서드 — BadSquare가 들어오면 기대 동작이 깨진다
    static void resizeAndPrint(Rectangle.BadRectangle r, int newWidth, int newHeight) {
        r.setWidth(newWidth);
        r.setHeight(newHeight);
        int expected = newWidth * newHeight;
        int actual   = r.area();
        System.out.println(r + " → 예상 넓이: " + expected + ", 실제 넓이: " + actual
                + (expected == actual ? " [정상]" : " [LSP 위반!]"));
    }

    public static void main(String[] args) {
        demonstrateViolation();
        demonstrateLSP();
    }

    static void demonstrateViolation() {
        System.out.println("=== LSP 위반 ===");

        var rect   = new Rectangle.BadRectangle(3, 4);
        var square = new Square.BadSquare(3);

        resizeAndPrint(rect, 5, 4);    // 정상 — 5×4 = 20
        resizeAndPrint(square, 5, 4);  // 위반 — setHeight(4)가 width도 4로 변경 → 4×4 = 16

        System.out.println("→ BadSquare는 BadRectangle을 완벽히 대체할 수 없다\n");
    }

    static void demonstrateLSP() {
        System.out.println("=== LSP 개선 — 공통 Shape 인터페이스 ===");

        List<Rectangle.Shape> shapes = List.of(
            new Rectangle.GoodRectangle(5, 4),
            new Square.GoodSquare(4),
            new Rectangle.GoodRectangle(3, 7),
            new Square.GoodSquare(6)
        );

        // Shape 인터페이스를 통해 모두 동일하게 처리 — 완벽 대체
        for (Rectangle.Shape shape : shapes) {
            System.out.println(shape.describe());
        }

        int totalArea = shapes.stream().mapToInt(Rectangle.Shape::area).sum();
        System.out.println("전체 넓이 합: " + totalArea);
        System.out.println("→ 모든 Shape 구현체가 완벽히 대체 가능");
    }
}
