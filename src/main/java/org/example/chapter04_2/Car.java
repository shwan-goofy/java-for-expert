package org.example.chapter04_2;

/**
 * 합성(Composition) 예시.
 * Car는 Engine을 내부에서 직접 생성한다 — 강한 Has-a 관계.
 * Car가 소멸되면 Engine도 소멸된다.
 */
public class Car {

    private final String model;
    private final Engine engine;  // 합성 — Car 생성 시 Engine도 함께 생성

    public Car(String model, int displacement) {
        this.model  = model;
        this.engine = new Engine(displacement);  // Car 내부에서 직접 생성
    }

    // Engine 메서드에 위임 (Delegation)
    public void start() {
        System.out.println(model + " 시동 시작");
        engine.start();
    }

    public void stop() {
        engine.stop();
        System.out.println(model + " 시동 종료");
    }

    public boolean isRunning() { return engine.isRunning(); }
    public String getModel()   { return model; }

    @Override
    public String toString() {
        return "Car{model='" + model + "', " + engine + "}";
    }
}
