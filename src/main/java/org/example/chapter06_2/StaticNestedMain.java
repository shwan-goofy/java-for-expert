package org.example.chapter06_2;

/**
 * Chapter 6-2: 정적 중첩 클래스 실행 진입점.
 *
 * <p>non-static 내부 클래스와 static 중첩 클래스의 생성 방식 차이를 비교하고
 * LinkedList, HttpRequest.Builder 예제를 시연한다.
 */
public class StaticNestedMain {

    // -------------------------------------------------------------------------
    // 비교 예시: non-static vs static 중첩 클래스
    // -------------------------------------------------------------------------

    static class Container {
        private String info = "container-state";

        /** non-static: Container 인스턴스의 info에 접근 가능 */
        class NonStaticNested {
            void show() {
                System.out.println("NonStaticNested — 외부 info: " + info);
            }
        }

        /** static: Container 인스턴스 참조 없음 — info에 접근 불가 */
        static class StaticNested {
            void show() {
                System.out.println("StaticNested — 외부 인스턴스 참조 없음");
            }
        }
    }

    // -------------------------------------------------------------------------
    // 시연 메서드
    // -------------------------------------------------------------------------

    private static void demonstrateCreation() {
        System.out.println("=== non-static vs static 생성 방식 비교 ===");

        Container container = new Container();

        // non-static: 반드시 외부 인스턴스로부터 생성
        Container.NonStaticNested nonStatic = container.new NonStaticNested();
        nonStatic.show();

        // static: 외부 인스턴스 없이 직접 생성
        Container.StaticNested staticNested = new Container.StaticNested();
        staticNested.show();
    }

    private static void demonstrateLinkedList() {
        System.out.println("\n=== LinkedList — static Node 예제 ===");

        LinkedList<String> list = new LinkedList<>();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        list.addFirst("Z");

        System.out.print("초기 상태: ");
        list.printAll();

        String removed = list.removeFirst();
        System.out.println("removeFirst() → " + removed);
        System.out.print("제거 후: ");
        list.printAll();

        System.out.println("크기: " + list.size());
    }

    private static void demonstrateHttpRequest() {
        System.out.println("\n=== HttpRequest.Builder — static nested Builder 패턴 ===");

        // Builder는 static → HttpRequest 인스턴스 없이 new HttpRequest.Builder()
        HttpRequest getRequest = new HttpRequest.Builder()
                .url("https://api.example.com/users")
                .header("Accept", "application/json")
                .build();

        System.out.println("GET 요청: " + getRequest);

        HttpRequest postRequest = new HttpRequest.Builder()
                .url("https://api.example.com/users")
                .method("POST")
                .header("Content-Type", "application/json")
                .body("{\"name\": \"Alice\"}")
                .build();

        System.out.println("POST 요청: " + postRequest);

        // url 누락 시 예외
        try {
            new HttpRequest.Builder().build();
        } catch (IllegalStateException e) {
            System.out.println("검증 실패: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        demonstrateCreation();
        demonstrateLinkedList();
        demonstrateHttpRequest();
    }
}
