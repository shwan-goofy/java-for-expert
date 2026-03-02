package org.example.chapter04_1;

/**
 * Animal을 상속하는 Dog 클래스.
 * extends, super(), @Override 사용법.
 */
public class Dog extends Animal {

    private final String breed;
    private boolean trained;

    public Dog(String name, int age, String breed) {
        super(name, age);  // 부모 생성자 호출 — 반드시 첫 번째 문장
        this.breed   = breed;
        this.trained = false;
    }

    public String getBreed()    { return breed;   }
    public boolean isTrained()  { return trained; }

    public void train() {
        this.trained = true;
        System.out.println(name + "가 훈련을 완료했습니다");
    }

    public void fetch() {
        System.out.println(name + "가 공을 가져옵니다!");
    }

    @Override
    public String speak() {
        return name + ": 멍멍!";
    }

    @Override
    public String describe() {
        return super.describe() + ", 품종: " + breed + ", 훈련 여부: " + trained;
    }

    @Override
    public String toString() {
        return "Dog{name='" + name + "', breed='" + breed + "', trained=" + trained + "}";
    }
}
