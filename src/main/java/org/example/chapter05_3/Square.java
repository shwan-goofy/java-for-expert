package org.example.chapter05_3;

/**
 * LSP 위반 Square와 개선된 Square.
 */
public class Square {

    // ----------------------------------------------------------------
    // LSP 위반 — BadRectangle을 상속해 불변식 유지를 위해 setWidth/setHeight를 재정의
    // ----------------------------------------------------------------
    public static class BadSquare extends Rectangle.BadRectangle {

        public BadSquare(int side) {
            super(side, side);
        }

        @Override
        public void setWidth(int width) {
            this.width  = width;
            this.height = width;  // 정사각형 불변식 유지
        }

        @Override
        public void setHeight(int height) {
            this.width  = height;
            this.height = height;  // 정사각형 불변식 유지
        }
    }

    // ----------------------------------------------------------------
    // LSP 개선 — Rectangle.Shape 인터페이스를 독립 구현
    // ----------------------------------------------------------------
    public static class GoodSquare implements Rectangle.Shape {
        private final int side;

        public GoodSquare(int side) {
            if (side <= 0) throw new IllegalArgumentException("변의 길이는 양수여야 합니다");
            this.side = side;
        }

        public int getSide() { return side; }

        @Override
        public int area() { return side * side; }

        @Override
        public String describe() {
            return "정사각형[" + side + "×" + side + ", 넓이=" + area() + "]";
        }
    }
}
