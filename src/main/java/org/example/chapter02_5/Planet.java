package org.example.chapter02_5;

/**
 * 계산 로직이 포함된 enum 예시.
 * 각 행성의 중력 가속도와 표면 무게를 계산한다.
 */
public enum Planet {

    MERCURY(3.303e+23, 2.4397e6),
    VENUS  (4.869e+24, 6.0518e6),
    EARTH  (5.976e+24, 6.37814e6),
    MARS   (6.421e+23, 3.3972e6),
    JUPITER(1.9e+27,   7.1492e7),
    SATURN (5.688e+26, 6.0268e7),
    URANUS (8.686e+25, 2.5559e7),
    NEPTUNE(1.024e+26, 2.4746e7);

    private static final double G = 6.67300E-11;  // 중력 상수

    private final double mass;    // 질량 (kg)
    private final double radius;  // 반지름 (m)

    Planet(double mass, double radius) {
        this.mass   = mass;
        this.radius = radius;
    }

    /** 표면 중력 가속도 (m/s^2) */
    public double surfaceGravity() {
        return G * mass / (radius * radius);
    }

    /** 지구 기준 질량(kg)을 받아 이 행성에서의 무게(N) 반환 */
    public double surfaceWeight(double earthWeight) {
        return earthWeight * surfaceGravity() / EARTH.surfaceGravity();
    }

    public double getMass()   { return mass;   }
    public double getRadius() { return radius; }
}
