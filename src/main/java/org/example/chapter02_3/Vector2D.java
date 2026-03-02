package org.example.chapter02_3;

import java.util.Objects;

/**
 * toString(), equals(), hashCode(), Comparable 직접 구현 예시.
 */
public class Vector2D implements Comparable<Vector2D> {

    private final double x;
    private final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    /** 벡터 크기 (magnitude) */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /** 벡터 덧셈 */
    public Vector2D add(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    /** 벡터 뺄셈 */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    /** 스칼라 곱 */
    public Vector2D scale(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    // ----------------------------------------------------------------
    // toString — Text Block 활용 (JDK 15+)
    // ----------------------------------------------------------------
    @Override
    public String toString() {
        return """
                Vector2D { x=%.2f, y=%.2f, magnitude=%.4f }""".formatted(x, y, magnitude());
    }

    // ----------------------------------------------------------------
    // equals — 값 동등성 (두 필드 모두 비교)
    // ----------------------------------------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector = (Vector2D) o;
        return Double.compare(vector.x, x) == 0
                && Double.compare(vector.y, y) == 0;
    }

    // ----------------------------------------------------------------
    // hashCode — equals()와 동일한 필드로 계산
    // ----------------------------------------------------------------
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    // ----------------------------------------------------------------
    // Comparable — 벡터 크기(magnitude)로 자연 순서 정의
    // ----------------------------------------------------------------
    @Override
    public int compareTo(Vector2D other) {
        return Double.compare(this.magnitude(), other.magnitude());
    }
}
