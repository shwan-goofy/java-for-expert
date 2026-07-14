package org.example.chapter00_17;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class DateTimeMain {
    public static void main(String[] args) {
        // 1. LocalDate, LocalTime, LocalDateTime 생성과 불변성
        LocalDate today = LocalDate.of(2026, 7, 14);
        LocalTime meeting = LocalTime.of(14, 30);
        LocalDateTime scheduled = LocalDateTime.of(2026, 12, 25, 9, 0);

        System.out.println("today: " + today);
        System.out.println("meeting: " + meeting);
        System.out.println("scheduled: " + scheduled);

        // 2. 날짜 계산 - 불변 객체이므로 원본은 그대로, 반환값을 사용해야 함
        LocalDate nextWeek = today.plusWeeks(1);
        LocalDate lastMonth = today.minusMonths(1);
        System.out.println("today (원본 불변): " + today);
        System.out.println("nextWeek: " + nextWeek);
        System.out.println("lastMonth: " + lastMonth);

        // 3. 날짜 비교
        LocalDate date1 = LocalDate.of(2026, 7, 14);
        LocalDate date2 = LocalDate.of(2026, 12, 25);
        System.out.println("date1.isBefore(date2): " + date1.isBefore(date2));
        System.out.println("date1.isAfter(date2): " + date1.isAfter(date2));
        System.out.println("date1.equals(date2): " + date1.equals(date2));

        // 4. Period(날짜 간격), Duration(시간 간격)
        Period period = Period.between(date1, date2);
        System.out.println("기간: " + period.getMonths() + "개월 " + period.getDays() + "일");

        LocalTime t1 = LocalTime.of(9, 0);
        LocalTime t2 = LocalTime.of(18, 30);
        Duration duration = Duration.between(t1, t2);
        System.out.println("시간 간격: " + duration.toHours() + "시간 " + duration.toMinutesPart() + "분");

        // 5. DateTimeFormatter로 형식 지정 출력
        LocalDateTime dateTime = LocalDateTime.of(2026, 7, 14, 15, 30);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분");
        System.out.println("포맷 결과: " + dateTime.format(formatter));
    }
}
