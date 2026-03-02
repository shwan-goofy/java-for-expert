package org.example.chapter03_2;

/**
 * record로 재구현한 불변 온도 클래스.
 * compact constructor로 유효성 검사.
 */
public class TemperatureRecord {

    private static final double ABSOLUTE_ZERO = -273.15;

    /**
     * 불변 온도 record.
     * accessor: celsius() — get 접두사 없음.
     */
    public record Temperature(double celsius) {

        // compact constructor — 파라미터를 검사하고 필요시 정규화
        public Temperature {
            if (celsius < ABSOLUTE_ZERO) {
                throw new IllegalArgumentException(
                    "절대 영도(" + ABSOLUTE_ZERO + "°C) 이하는 불가: " + celsius);
            }
        }

        // 정적 팩토리 메서드들
        public static Temperature ofCelsius(double celsius) {
            return new Temperature(celsius);
        }

        public static Temperature ofFahrenheit(double fahrenheit) {
            return new Temperature((fahrenheit - 32) * 5.0 / 9.0);
        }

        public static Temperature ofKelvin(double kelvin) {
            return new Temperature(kelvin + ABSOLUTE_ZERO);
        }

        // 파생 값 메서드
        public double fahrenheit() { return celsius * 9.0 / 5.0 + 32; }
        public double kelvin()     { return celsius - ABSOLUTE_ZERO; }

        public boolean isFreezing() { return celsius <= 0; }
        public boolean isBoiling()  { return celsius >= 100; }

        @Override
        public String toString() {
            return String.format("Temperature[%.2f°C / %.2f°F / %.2fK]",
                    celsius, fahrenheit(), kelvin());
        }
    }
}
