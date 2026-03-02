package org.example.chapter04_2;

/**
 * 합성(Composition)에서 포함되는 객체.
 * Car가 생성할 때 내부에서 만들어진다 — Car와 생명주기가 같다.
 */
public class Engine {

    private final int displacement;  // 배기량 (cc)
    private boolean running;

    public Engine(int displacement) {
        this.displacement = displacement;
        this.running      = false;
    }

    public void start() {
        running = true;
        System.out.println("엔진 시동 (배기량: " + displacement + "cc)");
    }

    public void stop() {
        running = false;
        System.out.println("엔진 정지");
    }

    public boolean isRunning()      { return running;      }
    public int getDisplacement()    { return displacement; }

    @Override
    public String toString() {
        return "Engine{displacement=" + displacement + "cc, running=" + running + "}";
    }
}
