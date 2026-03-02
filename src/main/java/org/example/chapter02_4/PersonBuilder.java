package org.example.chapter02_4;

import lombok.Builder;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

/**
 * Builder 패턴 직접 구현과 Lombok @Builder 비교.
 */
public class PersonBuilder {

    // ----------------------------------------------------------------
    // 직접 구현한 Builder
    // ----------------------------------------------------------------
    public static class PersonManual {

        private final String name;
        private final int age;
        private final String email;
        private final String role;
        private final List<String> tags;

        private PersonManual(Builder builder) {
            this.name  = builder.name;
            this.age   = builder.age;
            this.email = builder.email;
            this.role  = builder.role;
            this.tags  = List.copyOf(builder.tags);
        }

        public static Builder builder() { return new Builder(); }

        public String getName()       { return name;  }
        public int    getAge()        { return age;   }
        public String getEmail()      { return email; }
        public String getRole()       { return role;  }
        public List<String> getTags() { return tags;  }

        @Override
        public String toString() {
            return "PersonManual{name='" + name + "', age=" + age
                    + ", email='" + email + "', role='" + role
                    + "', tags=" + tags + "}";
        }

        public static class Builder {

            private String name;
            private int age = 0;
            private String email = "";
            private String role = "USER";
            private List<String> tags = List.of();

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder age(int age) {
                this.age = age;
                return this;
            }

            public Builder email(String email) {
                this.email = email;
                return this;
            }

            public Builder role(String role) {
                this.role = role;
                return this;
            }

            public Builder tags(List<String> tags) {
                this.tags = tags;
                return this;
            }

            public PersonManual build() {
                if (name == null || name.isBlank()) {
                    throw new IllegalStateException("이름은 필수입니다");
                }
                return new PersonManual(this);
            }
        }
    }

    // ----------------------------------------------------------------
    // Lombok @Builder 사용
    // ----------------------------------------------------------------
    @Builder
    @ToString
    public static class PersonLombok {

        private final String name;
        private final int age;
        @Builder.Default
        private final String email = "";
        @Builder.Default
        private final String role = "USER";
        @Singular
        private final List<String> tags;
    }
}
