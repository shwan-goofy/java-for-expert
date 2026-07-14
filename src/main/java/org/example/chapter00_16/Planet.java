package org.example.chapter00_16;

public enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    EARTH(5.976e+24, 6.37814e6),
    JUPITER(1.9e+27, 7.1492e7);

    private final double mass;   // kg
    private final double radius; // m

    Planet(double mass, double radius) { // enum 생성자는 항상 private
        this.mass = mass;
        this.radius = radius;
    }

    public double surfaceGravity() {
        final double g = 6.67300E-11;
        return g * mass / (radius * radius);
    }
}
