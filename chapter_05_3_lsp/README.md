# Chapter 5-3: LSP (리스코프 치환 원칙)

## 학습 목표

- LSP(Liskov Substitution Principle)의 의미를 이해한다
- `Square extends Rectangle` 안티패턴에서 LSP 위반을 인식한다
- 공통 추상화로 LSP를 만족하는 설계로 개선한다

## 핵심 개념

> **"자식 클래스는 부모 클래스의 역할을 완벽히 대체할 수 있어야 한다."**

부모 타입을 사용하는 코드에 자식 타입을 넣어도 프로그램이 올바르게 동작해야 한다.

### 위반 예시 — Square extends Rectangle

```java
public class Rectangle {
    protected int width, height;
    public void setWidth(int w)  { this.width = w; }
    public void setHeight(int h) { this.height = h; }
    public int area() { return width * height; }
}

public class Square extends Rectangle {
    @Override public void setWidth(int w)  { width = height = w; }  // 정사각형 불변식 유지
    @Override public void setHeight(int h) { width = height = h; }
}

// Rectangle을 기대하는 코드
void testRectangle(Rectangle r) {
    r.setWidth(5);
    r.setHeight(4);
    assert r.area() == 20;  // Square에서는 25 → 기대 동작 깨짐 (LSP 위반)
}
```

### 개선 예시 — 공통 추상화

```java
public interface Shape {
    int area();
}

public class Rectangle implements Shape { ... }  // 독립
public class Square implements Shape { ... }     // 독립
```

## 실습 예제

- `Rectangle.java`: LSP 위반 버전 + 독립 구현 버전
- `Square.java`: LSP 위반 버전 + 독립 구현 버전
- `LspMain.java`: 위반 케이스 시연 → 개선 케이스 확인

## 다음 챕터

[Chapter 5-4: ISP (인터페이스 분리 원칙)](../chapter_05_4_isp/)
