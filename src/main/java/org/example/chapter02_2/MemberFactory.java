package org.example.chapter02_2;

/**
 * 정적 팩토리 메서드 패턴 예시.
 * 생성 의도를 메서드 이름으로 표현한다.
 */
public class MemberFactory {

    public enum Role { USER, ADMIN, MANAGER }

    private final String name;
    private final String email;
    private final Role role;
    private final boolean active;

    // 생성자를 private으로 — 정적 팩토리 메서드를 통해서만 생성
    private MemberFactory(String name, String email, Role role, boolean active) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다");
        }
        this.name   = name;
        this.email  = email;
        this.role   = role;
        this.active = active;
    }

    // 정적 팩토리 메서드들 — 이름으로 생성 의도를 명확히 전달

    /** 일반 사용자 생성 */
    public static MemberFactory ofUser(String name, String email) {
        return new MemberFactory(name, email, Role.USER, true);
    }

    /** 관리자 생성 */
    public static MemberFactory ofAdmin(String name, String email) {
        return new MemberFactory(name, email, Role.ADMIN, true);
    }

    /** 매니저 생성 */
    public static MemberFactory ofManager(String name, String email) {
        return new MemberFactory(name, email, Role.MANAGER, true);
    }

    /** 비활성 계정으로 생성 */
    public static MemberFactory ofInactive(String name, String email, Role role) {
        return new MemberFactory(name, email, role, false);
    }

    // 인스턴스 메서드
    public MemberFactory activate() {
        return new MemberFactory(name, email, role, true);
    }

    public MemberFactory deactivate() {
        return new MemberFactory(name, email, role, false);
    }

    public boolean isActive()  { return active; }
    public String  getName()   { return name;   }
    public String  getEmail()  { return email;  }
    public Role    getRole()   { return role;   }

    // static 유틸리티 메서드
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    @Override
    public String toString() {
        return "Member{name='" + name + "', role=" + role + ", active=" + active + "}";
    }
}
