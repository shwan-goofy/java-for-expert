package org.example.chapter02_5;

/**
 * 필드와 메서드를 가진 기본 enum 예시.
 */
public enum Direction {

    NORTH(0, "북"),
    EAST(90, "동"),
    SOUTH(180, "남"),
    WEST(270, "서");

    private final int angle;
    private final String label;

    Direction(int angle, String label) {
        this.angle = angle;
        this.label = label;
    }

    public int getAngle() { return angle; }
    public String getLabel() { return label; }

    /** 반대 방향 반환 */
    public Direction opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST  -> WEST;
            case WEST  -> EAST;
        };
    }

    /** 시계 방향 다음 방향 */
    public Direction next() {
        var values = Direction.values();
        return values[(ordinal() + 1) % values.length];
    }

    @Override
    public String toString() {
        return label + "(" + angle + "도)";
    }
}
