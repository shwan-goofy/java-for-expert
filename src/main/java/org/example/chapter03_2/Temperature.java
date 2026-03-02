package org.example.chapter03_2;

/**
 * 전통적인 getter/setter 패턴 예시.
 * setter 내 유효성 검사로 불변식 보호.
 */
public class Temperature {

    private static final double ABSOLUTE_ZERO = -273.15;

    private double celsius;

    public Temperature(double celsius) {
        setCelsius(celsius);  // setter를 통해 초기화 — 유효성 검사 재사용
    }

    // getter
    public double getCelsius() { return celsius; }

    // setter — 유효성 검사 포함
    public void setCelsius(double celsius) {
        if (celsius < ABSOLUTE_ZERO) {
            throw new IllegalArgumentException(
                "절대 영도(" + ABSOLUTE_ZERO + "°C) 이하는 불가: " + celsius);
        }
        this.celsius = celsius;
    }

    // 화씨 getter — 파생 값
    public double getFahrenheit() {
        return celsius * 9.0 / 5.0 + 32;
    }

    // 화씨 setter
    public void setFahrenheit(double fahrenheit) {
        setCelsius((fahrenheit - 32) * 5.0 / 9.0);
    }

    // 켈빈 getter
    public double getKelvin() {
        return celsius - ABSOLUTE_ZERO;
    }

    public boolean isFreezing() { return celsius <= 0; }
    public boolean isBoiling()  { return celsius >= 100; }

    @Override
    public String toString() {
        return String.format("Temperature{%.2f°C / %.2f°F / %.2fK}",
                celsius, getFahrenheit(), getKelvin());
    }
}
