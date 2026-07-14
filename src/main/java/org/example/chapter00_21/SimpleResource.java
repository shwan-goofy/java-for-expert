package org.example.chapter00_21;

public class SimpleResource implements AutoCloseable {
    private final String name;

    public SimpleResource(String name) {
        this.name = name;
        System.out.println(name + " 자원 열기");
    }

    public void use() {
        System.out.println(name + " 자원 사용 중");
    }

    @Override
    public void close() {
        System.out.println(name + " 자원 닫기"); // try-with-resources 블록이 끝나면 자동 호출됨
    }
}
