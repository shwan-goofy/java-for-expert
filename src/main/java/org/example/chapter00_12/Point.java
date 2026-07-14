package org.example.chapter00_12;

import java.util.Objects;

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // 1. 자기 자신과 비교
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // 2. null 이거나 타입이 다르면 false
        }
        Point other = (Point) obj; // 3. 캐스팅
        return this.x == other.x && this.y == other.y; // 4. 필드 값 비교
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{x=" + x + ", y=" + y + "}";
    }
}
