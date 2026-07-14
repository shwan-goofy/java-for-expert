package org.example.chapter00_12;

// equals/hashCode/toString을 재정의하지 않은 클래스 — Object의 기본 구현과 비교하기 위한 예제
public class RawPoint {
    private final int x;
    private final int y;

    public RawPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
