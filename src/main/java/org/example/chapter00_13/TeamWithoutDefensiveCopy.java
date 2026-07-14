package org.example.chapter00_13;

import java.util.List;

// 방어적 복사가 없는 경우 — 외부에서 원본 리스트를 바꾸면 내부 상태도 함께 바뀐다
public final class TeamWithoutDefensiveCopy {
    private final List<String> members;

    public TeamWithoutDefensiveCopy(List<String> members) {
        this.members = members; // 방어적 복사 없이 참조를 그대로 저장
    }

    public List<String> getMembers() {
        return members; // 원본 리스트를 그대로 반환
    }
}
