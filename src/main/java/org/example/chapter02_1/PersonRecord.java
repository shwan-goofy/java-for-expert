package org.example.chapter02_1;

/**
 * record를 사용한 불변 데이터 클래스 예시 (JDK 16+).
 * 컴파일러가 생성자, accessor, equals(), hashCode(), toString()을 자동으로 만든다.
 */
public class PersonRecord {

    /**
     * 기본 record — 컴파일러가 모든 보일러플레이트를 생성한다.
     */
    public record SimplePoint(int x, int y) {}

    /**
     * compact constructor로 유효성 검사가 포함된 record.
     * compact constructor는 파라미터 목록을 생략하고,
     * 블록 안에서 파라미터 값을 변경하면 자동으로 this.x = x; 처리된다.
     */
    public record PositivePoint(int x, int y) {
        public PositivePoint {
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException(
                    "좌표는 0 이상이어야 합니다: x=" + x + ", y=" + y);
            }
        }
    }

    /**
     * 불변 회원 정보 record.
     * accessor 메서드 이름은 필드명과 동일 (get 접두사 없음).
     */
    public record MemberInfo(String name, int age, String email) {
        public MemberInfo {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("이름은 비어 있을 수 없습니다");
            }
            if (age < 0 || age > 150) {
                throw new IllegalArgumentException("유효하지 않은 나이: " + age);
            }
        }

        // record에 인스턴스 메서드 추가 가능
        public boolean isAdult() {
            return age >= 18;
        }

        public String maskedEmail() {
            if (email == null || !email.contains("@")) {
                return "***";
            }
            int atIndex = email.indexOf('@');
            return email.substring(0, Math.min(3, atIndex)) + "***" + email.substring(atIndex);
        }
    }
}
