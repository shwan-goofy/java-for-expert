package org.example;

import org.example.chapter01_1.Human;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog("튀김이", 1, true);
        Human h = new Human("이승환", 25, true);

        dog.jump();
        h.jump();
    }

    public static class Animal {
        public String name;
        public Integer age;

        Animal(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public void speak() {
            System.out.println("동물이 말을 합니다!!");
        }

        public void jump() {
            System.out.println("점프!!");
        }
    }

    public static class Dog extends Animal {
        public boolean tail;

        public Dog(String name, Integer age, boolean tail) {
            super(name, age);
            this.tail = tail;
        }

        @Override
        public void speak() {
            System.out.println("멍멍");
        }
    }

    public static class Human extends Animal {
        public boolean hand;

        public Human(String name, Integer age, boolean hand) {
            super(name, age);
            this.hand = hand;
        }

        @Override
        public void speak() {
            System.out.println("안녕하세요 저는 사람입니다");
        }
    }
}

