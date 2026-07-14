package org.example.chapter00_13;

import java.util.ArrayList;
import java.util.List;

// 참조형(List) 필드를 가진 불변 클래스 — 방어적 복사로 불변성을 지킨다
public final class Team {
    private final List<String> members;

    public Team(List<String> members) {
        this.members = new ArrayList<>(members); // 방어적 복사: 외부 리스트와 연결을 끊는다
    }

    public List<String> getMembers() {
        return List.copyOf(members); // 수정 불가능한 뷰를 반환 — 외부에서 내부 리스트를 바꿀 수 없다
    }
}
