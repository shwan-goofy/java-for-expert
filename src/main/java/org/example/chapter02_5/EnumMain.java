package org.example.chapter02_5;

import java.util.EnumMap;
import java.util.EnumSet;

public class EnumMain {

    public static void main(String[] args) {
        demonstrateDirection();
        demonstratePlanet();
        demonstrateOrderStatus();
        demonstrateEnumCollections();
    }

    // ----------------------------------------------------------------
    // 1. Direction — 필드와 메서드를 가진 enum
    // ----------------------------------------------------------------
    static void demonstrateDirection() {
        System.out.println("=== Direction enum ===");

        for (Direction d : Direction.values()) {
            System.out.printf("%-5s → 반대: %-5s, 다음: %-5s%n",
                    d, d.opposite(), d.next());
        }

        // switch expression과 enum
        Direction dir = Direction.NORTH;
        String description = switch (dir) {
            case NORTH -> "위쪽으로 이동";
            case SOUTH -> "아래쪽으로 이동";
            case EAST  -> "오른쪽으로 이동";
            case WEST  -> "왼쪽으로 이동";
        };
        System.out.println(dir + ": " + description);

        // valueOf() 사용
        Direction fromString = Direction.valueOf("EAST");
        System.out.println("valueOf(\"EAST\"): " + fromString);
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 2. Planet — 계산 로직을 담은 enum
    // ----------------------------------------------------------------
    static void demonstratePlanet() {
        System.out.println("=== Planet enum ===");

        double earthWeight = 75.0;  // kg
        System.out.printf("지구 무게 %.1fkg 기준 행성별 무게:%n", earthWeight);

        for (Planet p : Planet.values()) {
            System.out.printf("  %-10s : %.2fkg (중력: %.2f m/s²)%n",
                    p.name(),
                    p.surfaceWeight(earthWeight),
                    p.surfaceGravity());
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 3. OrderStatus — 추상 메서드와 상태 전이
    // ----------------------------------------------------------------
    static void demonstrateOrderStatus() {
        System.out.println("=== OrderStatus enum ===");

        var status = OrderStatus.PENDING;
        System.out.println("현재 상태: " + status);
        System.out.println("메시지: " + status.processMessage());
        System.out.println("전환 가능: " + status.allowedTransitions());

        // 정상 전이
        status = status.transitionTo(OrderStatus.CONFIRMED);
        System.out.println("\n→ " + status + ": " + status.processMessage());

        status = status.transitionTo(OrderStatus.SHIPPED);
        System.out.println("→ " + status + ": " + status.processMessage());

        status = status.transitionTo(OrderStatus.DELIVERED);
        System.out.println("→ " + status + ": " + status.processMessage());

        // 불가능한 전이 — 예외
        try {
            status.transitionTo(OrderStatus.CANCELLED);
        } catch (IllegalStateException e) {
            System.out.println("\n예외 확인: " + e.getMessage());
        }
        System.out.println();
    }

    // ----------------------------------------------------------------
    // 4. EnumSet / EnumMap
    // ----------------------------------------------------------------
    static void demonstrateEnumCollections() {
        System.out.println("=== EnumSet / EnumMap ===");

        // EnumSet — 비트 벡터 기반, 매우 빠름
        var cardinals = EnumSet.of(Direction.NORTH, Direction.SOUTH,
                Direction.EAST, Direction.WEST);
        System.out.println("모든 방향: " + cardinals);

        var northSouth = EnumSet.of(Direction.NORTH, Direction.SOUTH);
        System.out.println("포함 여부 NORTH: " + cardinals.contains(Direction.NORTH));

        var complement = EnumSet.complementOf(northSouth);
        System.out.println("보집합: " + complement);

        // EnumMap — enum 키에 최적화된 Map
        var directionLabels = new EnumMap<Direction, String>(Direction.class);
        for (Direction d : Direction.values()) {
            directionLabels.put(d, d.getLabel() + "(" + d.getAngle() + "°)");
        }
        System.out.println("\nEnumMap 내용:");
        directionLabels.forEach((k, v) -> System.out.println("  " + k.name() + " → " + v));
    }
}
