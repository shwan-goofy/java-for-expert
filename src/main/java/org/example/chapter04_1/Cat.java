package org.example.chapter04_1;

/**
 * Animal을 상속하는 Cat 클래스.
 */
public class Cat extends Animal {

    private final boolean indoor;
    private int lives;

    public Cat(String name, int age, boolean indoor) {
        super(name, age);
        this.indoor = indoor;
        this.lives  = 9;
    }

    public boolean isIndoor() { return indoor; }
    public int getLives()     { return lives;  }

    public void loseLife() {
        if (lives > 0) {
            lives--;
            System.out.println(name + "의 남은 생명: " + lives);
        }
    }

    @Override
    public String speak() {
        return name + ": 야옹~";
    }

    @Override
    public String describe() {
        return super.describe() + ", 실내 여부: " + indoor + ", 남은 생명: " + lives;
    }

    @Override
    public String toString() {
        return "Cat{name='" + name + "', indoor=" + indoor + ", lives=" + lives + "}";
    }
}
