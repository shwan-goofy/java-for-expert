# Chapter 0-2: Hello World

## 학습 목표

- 자바 프로그램의 기본 골격(클래스, `main` 메서드)을 이해한다
- `System.out.println`으로 값을 출력하는 방법을 익힌다
- 주석의 종류와 사용법을 익힌다
- 컴파일(`javac`)과 실행(`java`)의 흐름을 이해한다

## Python과의 비교

| Python | Java |
|---|---|
| `print("Hello")` | `System.out.println("Hello");` |
| 파일 최상단에 바로 코드 작성 | 반드시 클래스 안에 작성 |
| `# 주석` | `// 주석`, `/* 주석 */`, `/** 문서 주석 */` |
| `python hello.py` 한 번에 실행 | `javac Hello.java` 후 `java Hello` (2단계) |

## 핵심 개념

### 1. 가장 단순한 자바 프로그램

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

- `public class HelloWorld`: 파일 이름과 클래스 이름은 반드시 동일해야 한다 (`HelloWorld.java`).
- `public static void main(String[] args)`: 프로그램이 시작되는 진입점(entry point). 이름, 형식이 고정되어 있다.
- `System.out.println(...)`: 괄호 안의 값을 출력하고 줄바꿈한다. 줄바꿈 없이 출력하려면 `System.out.print(...)`를 사용한다.

### 2. 컴파일과 실행

```bash
javac HelloWorld.java   # 소스 코드를 바이트코드(HelloWorld.class)로 컴파일
java HelloWorld         # JVM이 바이트코드를 실행 (확장자 .class는 붙이지 않는다)
```

이 프로젝트는 Gradle을 사용하므로 실제로는 IDE의 Run 버튼이나 `./gradlew run` 등으로 실행한다.

### 3. 주석(Comment)

```java
// 한 줄 주석

/*
 * 여러 줄 주석
 */

/**
 * 문서 주석(Javadoc) — 클래스나 메서드 설명에 사용
 */
```

### 4. print vs println

```java
System.out.print("A");
System.out.print("B");
// 출력: AB (줄바꿈 없음)

System.out.println("A");
System.out.println("B");
// 출력:
// A
// B
```

## 실습 예제

`HelloWorldMain.java`에서는 다음을 학습한다:
1. `print`와 `println`의 차이
2. 문자열과 숫자를 함께 출력하는 방법 (`+` 연결)
3. 세 종류의 주석 사용법

## 주의사항

- 파일명과 `public` 클래스명은 반드시 일치해야 한다.
- 모든 실행 가능한 문장은 세미콜론(`;`)으로 끝나야 한다.
- 자바는 대소문자를 구분한다 (`String` ≠ `string`).

## 다음 챕터

[Chapter 0-3: 변수](../chapter_00_3_variables/)에서 자바의 데이터 타입과 변수 선언 방법을 배운다.
