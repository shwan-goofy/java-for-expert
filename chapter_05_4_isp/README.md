# Chapter 5-4: ISP (인터페이스 분리 원칙)

## 학습 목표

- ISP(Interface Segregation Principle)의 의미를 이해한다
- 비대한 인터페이스가 야기하는 문제를 인식한다
- 클라이언트가 실제로 사용하는 메서드만 포함하는 작은 인터페이스로 분리한다

## 핵심 개념

> **"클라이언트는 자신이 사용하지 않는 인터페이스에 의존하면 안 된다."**

하나의 거대한 인터페이스보다 여러 개의 구체적인 인터페이스가 낫다.

### 위반 예시

```java
// 비대한 인터페이스 — 복합기만 모든 메서드를 구현 가능
interface MultiFunctionDevice {
    void print(String doc);
    void scan(String doc);
    void fax(String doc);
    void copy(String doc);
}

// 단순 프린터는 scan, fax, copy가 불필요하지만 구현 강제됨
class SimplePrinter implements MultiFunctionDevice {
    @Override public void print(String doc) { ... }
    @Override public void scan(String doc)  { throw new UnsupportedOperationException(); }
    @Override public void fax(String doc)   { throw new UnsupportedOperationException(); }
    @Override public void copy(String doc)  { throw new UnsupportedOperationException(); }
}
```

### 개선 예시

```java
interface Printable { void print(String doc); }
interface Scannable { void scan(String doc);  }
interface Faxable   { void fax(String doc);   }

class SimplePrinter implements Printable { ... }  // 필요한 것만
class AllInOnePrinter implements Printable, Scannable, Faxable { ... }
```

## 실습 예제

- `Printable.java`, `Scannable.java`: 분리된 인터페이스
- `MultiFunctionDevice.java`: 여러 인터페이스를 조합한 복합기
- `IspMain.java`: 위반/개선 비교

## 다음 챕터

[Chapter 5-5: DIP (의존 역전 원칙)](../chapter_05_5_dip/)
