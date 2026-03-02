package org.example.chapter02_4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DSL 스타일 Builder — SQL SELECT 문 생성기.
 * Fluent Interface로 자연어처럼 읽히는 체이닝 제공.
 */
public class QueryBuilder {

    private final List<String> columns = new ArrayList<>();
    private String table;
    private final List<String> conditions = new ArrayList<>();
    private String orderByColumn;
    private boolean descending = false;
    private int limitValue = -1;
    private int offsetValue = -1;

    private QueryBuilder() {}

    // ----------------------------------------------------------------
    // DSL 진입점
    // ----------------------------------------------------------------

    /** SELECT 절 — 조회할 컬럼 지정 */
    public static QueryBuilder select(String... columns) {
        var qb = new QueryBuilder();
        qb.columns.addAll(Arrays.asList(columns));
        return qb;
    }

    /** SELECT * */
    public static QueryBuilder selectAll() {
        return select("*");
    }

    // ----------------------------------------------------------------
    // Fluent Interface — 각 메서드가 this를 반환해 체이닝 가능
    // ----------------------------------------------------------------

    /** FROM 절 */
    public QueryBuilder from(String table) {
        this.table = table;
        return this;
    }

    /** WHERE 절 조건 추가 (여러 번 호출 시 AND로 연결) */
    public QueryBuilder where(String condition) {
        this.conditions.add(condition);
        return this;
    }

    /** ORDER BY 절 (오름차순) */
    public QueryBuilder orderBy(String column) {
        this.orderByColumn = column;
        this.descending = false;
        return this;
    }

    /** ORDER BY 절 (내림차순) */
    public QueryBuilder orderByDesc(String column) {
        this.orderByColumn = column;
        this.descending = true;
        return this;
    }

    /** LIMIT 절 */
    public QueryBuilder limit(int count) {
        this.limitValue = count;
        return this;
    }

    /** OFFSET 절 */
    public QueryBuilder offset(int skip) {
        this.offsetValue = skip;
        return this;
    }

    // ----------------------------------------------------------------
    // 최종 빌드
    // ----------------------------------------------------------------

    public String build() {
        if (table == null || table.isBlank()) {
            throw new IllegalStateException("FROM 절의 테이블명은 필수입니다");
        }

        var sb = new StringBuilder();
        sb.append("SELECT ").append(String.join(", ", columns));
        sb.append(" FROM ").append(table);

        if (!conditions.isEmpty()) {
            sb.append(" WHERE ").append(String.join(" AND ", conditions));
        }

        if (orderByColumn != null) {
            sb.append(" ORDER BY ").append(orderByColumn);
            if (descending) sb.append(" DESC");
        }

        if (limitValue > 0) {
            sb.append(" LIMIT ").append(limitValue);
        }

        if (offsetValue > 0) {
            sb.append(" OFFSET ").append(offsetValue);
        }

        return sb.toString();
    }
}
