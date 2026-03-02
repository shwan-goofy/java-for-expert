package org.example.chapter03_2;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Lombok @Getter, @Setter, @Data 사용 예시.
 */
public class MemberLombok {

    /**
     * @Getter, @Setter를 선택적으로 적용.
     * id는 setter 없음 (생성 후 변경 불가).
     */
    @Getter
    @Setter
    @ToString
    public static class MemberSelective {

        @Setter(AccessLevel.NONE)  // id setter 생성 안 함
        private String id;

        private String name;
        private int age;

        @Getter(AccessLevel.NONE)  // password getter 생성 안 함
        private String password;

        public MemberSelective(String id, String name, int age, String password) {
            this.id       = id;
            this.name     = name;
            this.age      = age;
            this.password = password;
        }
    }

    /**
     * @Data — @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor.
     * final 필드(id)는 @RequiredArgsConstructor에 의해 생성자에 포함.
     */
    @Data
    public static class MemberData {

        private final String id;  // final — setter 생성 안 됨, 생성자에 포함
        private String name;
        private int age;
        private String email;
    }
}
