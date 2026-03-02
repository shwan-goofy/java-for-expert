
# [자바 프로그래밍 과제] 나만의 자동차 생산 공장 만들기 🚗

**■ 과제 목표**
지금까지 학습한 `class`, `static`, `생성자`, `메서드 체이닝(Method Chaining)`, `팩토리 메서드 패턴(Factory Method Pattern)`, `enum`을 모두 활용하여 자동차 객체를 생성하고 설정하는 프로그램을 작성합니다.

**■ 과제 시나리오**
당신은 첨단 자동차 공장의 소프트웨어 엔지니어입니다. 이 공장에서는 주문이 들어오면 특정 종류의 자동차를 뼈대부터 만든 뒤, 고객이 원하는 색상과 옵션을 차례대로 입히는 공정을 거칩니다. 안전한 생산을 위해 자동차 객체는 함부로 `new` 키워드로 생성할 수 없으며, 오직 '공장(Factory)' 역할을 하는 특정 메서드를 통해서만 만들어야 합니다.

### 📝 세부 요구사항

**1. `CarType` Enum 만들기**

* 자동차의 종류를 나타내는 `enum`을 생성하세요.
* 종류: `SEDAN`, `SUV`, `SPORTS_CAR`

**2. `Car` 클래스 만들기**

* **필드 (인스턴스 변수):**
* `CarType type`: 자동차의 종류
* `String color`: 자동차의 색상
* `String option`: 추가 옵션


* **static 필드 (클래스 변수):**
* `static int totalCarsProduced`: 공장에서 지금까지 생산된 총 자동차 대수를 추적하는 변수입니다. 초기값은 0입니다.



**3. 생성자 (Constructor) 제한하기**

* `Car` 클래스의 생성자는 **반드시 `private`으로 선언**하세요. (외부에서 `new Car()`를 할 수 없도록 막습니다.)
* 생성자는 `CarType`을 매개변수로 받아서 필드를 초기화해야 합니다.
* 생성자가 호출될 때마다 `totalCarsProduced` 값을 1씩 증가시키세요.

**4. 팩토리 메서드 (Factory Method) 구현하기**

* 객체 생성을 대신해 줄 `public static Car createCar(CarType type)` 메서드를 만드세요.
* 이 메서드 내부에서 `private` 생성자를 호출하여 자동차 객체를 만든 후 반환합니다.

**5. 메서드 체이닝 (Method Chaining) 구현하기**

* 자동차의 색상을 설정하는 `setColor(String color)` 메서드를 만드세요.
* 자동차의 옵션을 설정하는 `setOption(String option)` 메서드를 만드세요.
* **핵심:** 위 두 메서드는 설정이 끝난 후, 연속해서 다른 메서드를 호출할 수 있도록 **자기 자신(`this`)을 반환**해야 합니다. (반환 타입이 `Car`여야 합니다.)

**6. 정보 출력 메서드**

* 자동차의 현재 상태를 보기 좋게 출력하는 `public void printCarInfo()` 메서드를 작성하세요.
* `totalCarsProduced` 값을 반환하는 `public static int getTotalCarsProduced()` 메서드를 작성하세요.

---

### 💻 테스트 코드 (Main.java)

여러분이 작성한 `Car`와 `CarType`이 정상적으로 동작하는지 아래의 `Main` 클래스를 실행하여 확인해 보세요. (아래 코드가 에러 없이 돌아가고, 출력 예시와 똑같이 나와야 합니다.)

```java
public class Main {
    public static void main(String[] args) {
        // 1. 팩토리 메서드와 메서드 체이닝을 활용한 객체 생성
        Car mySportsCar = Car.createCar(CarType.SPORTS_CAR)
                             .setColor("Red")
                             .setOption("Sunroof");

        Car familySuv = Car.createCar(CarType.SUV)
                           .setColor("White")
                           .setOption("7 Seats");

        // 2. 자동차 정보 출력
        mySportsCar.printCarInfo();
        System.out.println("-----------------");
        familySuv.printCarInfo();
        System.out.println("-----------------");

        // 3. static 변수를 통한 총 생산량 확인
        System.out.println("총 생산된 자동차 수: " + Car.getTotalCarsProduced() + "대");
    }
}

```

**[기대되는 출력 결과]**

```text
차종: SPORTS_CAR
색상: Red
옵션: Sunroof
-----------------
차종: SUV
색상: White
옵션: 7 Seats
-----------------
총 생산된 자동차 수: 2대

```