package org.example.chapter01_1;

import lombok.Getter;
import lombok.ToString;

public class Human {
    @Getter
    private String name;
    @Getter
    private Integer age;
    private String residenceNumber;

    public Human(String name, Integer age, String residenceNumber) {
        this.name = name;
        this.age = age;
        this.residenceNumber = residenceNumber;
    }

    public String getResidenceNumber() {
        return "******-*******";
    }
}

