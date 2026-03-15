package org.example.chapter06_1;

import java.util.ArrayList;
import java.util.List;

/**
 * 실전 예제: 이벤트 처리를 내부 클래스로 구현한 버튼.
 *
 * <p>내부 클래스 {@link ClickHandler}는 버튼의 {@code label}에 자연스럽게 접근한다.
 * 별도의 참조 전달 없이 외부 클래스 상태를 공유하는 것이 내부 클래스의 핵심 장점이다.
 */
public class EventButton {

    private final String label;
    private int clickCount;
    private final List<String> eventLog;

    public EventButton(String label) {
        this.label = label;
        this.clickCount = 0;
        this.eventLog = new ArrayList<>();
    }

    /**
     * 클릭 이벤트를 처리하는 내부 클래스.
     *
     * <p>외부 클래스({@link EventButton})의 {@code label}, {@code clickCount},
     * {@code eventLog}에 직접 접근한다. EventButton 인스턴스 없이 단독으로 존재할 수 없다.
     */
    public class ClickHandler {

        private final String handlerName;

        public ClickHandler(String handlerName) {
            this.handlerName = handlerName;
        }

        /**
         * 버튼 클릭을 처리한다.
         * 외부 클래스의 clickCount를 증가시키고 로그를 기록한다.
         */
        public void onClick() {
            clickCount++;  // 외부 클래스의 private 상태 변경
            String log = "[" + handlerName + "] '" + label + "' 클릭 (총 " + clickCount + "회)";
            eventLog.add(log);  // 외부 클래스의 private 컬렉션에 직접 추가
            System.out.println(log);
        }

        /** 이 핸들러가 속한 버튼 정보를 반환한다. */
        public String getButtonInfo() {
            return "버튼='" + label + "', 클릭=" + clickCount + "회";
        }
    }

    /** 새로운 클릭 핸들러를 생성해 등록한다. */
    public ClickHandler addClickHandler(String handlerName) {
        return new ClickHandler(handlerName);
    }

    public String getLabel() {
        return label;
    }

    public int getClickCount() {
        return clickCount;
    }

    public List<String> getEventLog() {
        return List.copyOf(eventLog);
    }

    @Override
    public String toString() {
        return "EventButton{label='" + label + "', clickCount=" + clickCount + "}";
    }
}
