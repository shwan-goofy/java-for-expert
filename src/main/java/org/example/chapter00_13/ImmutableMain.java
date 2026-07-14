package org.example.chapter00_13;

import java.util.ArrayList;
import java.util.List;

public class ImmutableMain {
    public static void main(String[] args) {
        // 1. 가변 객체 공유의 문제
        System.out.println("--- 가변 객체(MutableMoney) ---");
        MutableMoney price = new MutableMoney(10000);
        MutableMoney reference = price; // 같은 객체를 참조
        reference.setAmount(0);
        System.out.println("price.getAmount(): " + price.getAmount()); // 0 — 의도치 않게 함께 변경됨

        // 2. 불변 객체의 add() — 새 객체를 반환
        System.out.println("--- 불변 객체(Money) ---");
        Money original = new Money(10000);
        Money discounted = original.add(-1000);
        System.out.println("original: " + original);       // Money{amount=10000} — 원본 불변
        System.out.println("discounted: " + discounted);   // Money{amount=9000} — 새 객체

        // 3. 방어적 복사가 없을 때의 문제
        System.out.println("--- 방어적 복사 없음 (TeamWithoutDefensiveCopy) ---");
        List<String> originalList = new ArrayList<>(List.of("철수", "영희"));
        TeamWithoutDefensiveCopy leakyTeam = new TeamWithoutDefensiveCopy(originalList);
        originalList.add("훈이"); // 외부에서 원본 리스트를 변경
        System.out.println("leakyTeam.getMembers(): " + leakyTeam.getMembers()); // 훈이까지 포함됨 — 불변성 깨짐

        // 4. 방어적 복사가 있을 때
        System.out.println("--- 방어적 복사 있음 (Team) ---");
        List<String> safeList = new ArrayList<>(List.of("철수", "영희"));
        Team safeTeam = new Team(safeList);
        safeList.add("훈이"); // 외부에서 원본 리스트를 변경해도
        System.out.println("safeTeam.getMembers(): " + safeTeam.getMembers()); // 훈이가 포함되지 않음 — 불변성 유지

        // getMembers()가 반환한 리스트를 수정하려 하면 예외가 발생한다 (unmodifiable)
        try {
            safeTeam.getMembers().add("몰래추가");
        } catch (UnsupportedOperationException e) {
            System.out.println("getMembers() 결과는 수정 불가: " + e.getClass().getSimpleName());
        }
    }
}
