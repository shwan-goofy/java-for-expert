# Chapter 0-17: 날짜와 시간

## 학습 목표

- `java.time` 패키지(자바 8+ 표준 날짜/시간 API)의 핵심 클래스를 이해한다
- `LocalDate`, `LocalTime`, `LocalDateTime`의 차이와 사용법을 익힌다
- 날짜/시간을 계산(더하기, 빼기, 비교)하는 방법을 익힌다
- `Duration`, `Period`로 시간 간격을 표현하는 방법을 익힌다
- 날짜/시간을 원하는 형식으로 출력하는 `DateTimeFormatter`를 사용한다

## Python과의 비교

| Python | Java |
|---|---|
| `datetime.date` | `LocalDate` |
| `datetime.time` | `LocalTime` |
| `datetime.datetime` | `LocalDateTime` |
| `timedelta` | `Duration`(시간 기반 간격), `Period`(날짜 기반 간격) |
| `strftime()` / `strptime()` | `DateTimeFormatter` |
| `datetime` 객체는 불변(immutable) | `java.time`의 모든 클래스는 불변([0-13](../chapter_00_13_immutable_object/) 참고) |

## 핵심 개념

### 1. LocalDate, LocalTime, LocalDateTime

이름 그대로 **시간대(timezone) 정보가 없는**(Local) 날짜/시간을 표현한다.

```java
LocalDate today = LocalDate.now();               // 오늘 날짜 (예: 2026-07-14)
LocalDate birthday = LocalDate.of(2000, 3, 15);  // 특정 날짜 생성

LocalTime now = LocalTime.now();                 // 현재 시각 (예: 14:30:00)
LocalTime meeting = LocalTime.of(14, 30);         // 14:30

LocalDateTime dateTime = LocalDateTime.now();     // 날짜 + 시간
LocalDateTime scheduled = LocalDateTime.of(2026, 12, 25, 9, 0); // 2026-12-25 09:00
```

이 클래스들은 [0-13에서 배운 불변 객체](../chapter_00_13_immutable_object/)다. 값을 바꾸는 메서드는
항상 **새 객체를 반환**한다.

### 2. 날짜/시간 계산

```java
LocalDate today = LocalDate.of(2026, 7, 14);

LocalDate nextWeek = today.plusWeeks(1);   // 1주일 후
LocalDate lastMonth = today.minusMonths(1); // 1개월 전
LocalDate nextYear = today.plusYears(1);    // 1년 후

System.out.println(today);      // 2026-07-14 — 원본은 그대로 (불변)
System.out.println(nextWeek);   // 2026-07-21 — 새 객체
```

### 3. 날짜/시간 비교

```java
LocalDate date1 = LocalDate.of(2026, 7, 14);
LocalDate date2 = LocalDate.of(2026, 12, 25);

date1.isBefore(date2); // true
date1.isAfter(date2);  // false
date1.isEqual(date2);  // false
date1.equals(date2);   // false (equals도 재정의되어 있어 사용 가능)
```

### 4. Duration과 Period — 시간 간격 표현

- **`Period`**: 날짜(연/월/일) 기준 간격
- **`Duration`**: 시간(시/분/초) 기준 간격

```java
LocalDate start = LocalDate.of(2026, 1, 1);
LocalDate end = LocalDate.of(2026, 7, 14);
Period period = Period.between(start, end);
System.out.println(period.getMonths() + "개월 " + period.getDays() + "일"); // 6개월 13일

LocalTime t1 = LocalTime.of(9, 0);
LocalTime t2 = LocalTime.of(18, 30);
Duration duration = Duration.between(t1, t2);
System.out.println(duration.toHours() + "시간 " + duration.toMinutesPart() + "분"); // 9시간 30분
```

### 5. DateTimeFormatter — 원하는 형식으로 출력

```java
LocalDateTime dateTime = LocalDateTime.of(2026, 7, 14, 15, 30);

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
String formatted = dateTime.format(formatter);
System.out.println(formatted); // 2026년 07월 14일 15시 30분
```

## 실습 예제

`DateTimeMain.java`에서는 다음을 학습한다:
1. `LocalDate`, `LocalTime`, `LocalDateTime` 생성과 불변성 확인
2. `plusXxx`/`minusXxx`로 날짜 계산
3. `isBefore`/`isAfter`/`isEqual`로 날짜 비교
4. `Period`, `Duration`으로 간격 계산
5. `DateTimeFormatter`로 형식 지정 출력

## 주의사항

- `java.time`의 모든 클래스는 불변이다. `date.plusDays(1)`을 호출해도 `date` 자체는 바뀌지 않으므로, 반드시 반환값을 사용해야 한다 (`date = date.plusDays(1);`).
- 옛날 자바의 `Date`, `Calendar` 클래스는 가변(mutable)이고 설계 결함이 많아 더 이상 사용을 권장하지 않는다. 새 코드는 항상 `java.time` 패키지를 사용한다.
- 날짜 간격은 `Period`(연/월/일), 시간 간격은 `Duration`(시/분/초/나노초)으로 구분해서 사용한다.

## 다음 챕터

[Chapter 0-18: 중첩 클래스, 내부 클래스 1](../chapter_00_18_nested_inner_1/)에서 클래스 안에 클래스를 선언하는 방법을 배운다.
