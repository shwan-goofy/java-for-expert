package org.example.chapter05_3;

/**
 * LSP 위반 예시와 개선 예시를 함께 담고 있다.
 */
public class Rectangle {

    // ----------------------------------------------------------------
    // LSP 위반 버전 — Square가 이 클래스를 상속하면 LSP가 깨진다
    // ----------------------------------------------------------------
    public static class BadRectangle {
        protected int width;
        protected int height;

        public BadRectangle(int width, int height) {
            this.width  = width;
            this.height = height;
        }

        public void setWidth(int width)   { this.width  = width;  }
        public void setHeight(int height) { this.height = height; }
        public int getWidth()  { return width;  }
        public int getHeight() { return height; }
        public int area()      { return width * height; }

        @Override
        public String toString() {
            return getClass().getSimpleName() + "[" + width + "×" + height + "]";
        }
    }

    // ----------------------------------------------------------------
    // LSP 개선 버전 — Shape 인터페이스를 구현 (Square와 무관)
    // ----------------------------------------------------------------
    public interface Shape {
        int area();
        String describe();
    }

    public static class GoodRectangle implements Shape {
        private final int width;
        private final int height;

        public GoodRectangle(int width, int height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("너비와 높이는 양수여야 합니다");
            }
            this.width  = width;
            this.height = height;
        }

        public int getWidth()  { return width;  }
        public int getHeight() { return height; }

        @Override
        public int area() { return width * height; }

        @Override
        public String describe() {
            return "직사각형[" + width + "×" + height + ", 넓이=" + area() + "]";
        }
    }
}
