package org.example.chapter01_3;

import java.io.IOException;

/**
 * Checked Exception과 try-with-resources 예시.
 */
public class CheckedExample {

    /**
     * AutoCloseable을 구현한 자원 — try-with-resources에서 자동으로 close() 호출.
     */
    public static class DatabaseConnection implements AutoCloseable {

        private final String url;
        private boolean open;

        public DatabaseConnection(String url) throws IOException { // 그 외 Exception 입니다...
            if (url == null || url.isBlank()) {
                throw new IOException("DB URL이 비어 있습니다");
            }
            this.url = url;
            this.open = true;
            System.out.println("DB 연결 열림: " + url);
        }

        public String query(String sql) throws IOException {
            if (!open) {
                throw new IOException("이미 닫힌 연결입니다");
            }
            return "결과: [" + sql + "] from " + url;
        }

        @Override
        public void close() {
            open = false;
            System.out.println("DB 연결 닫힘: " + url);
        }

        public void a() throws Exception {
            b(10); // 문제 없음
            try {
                b(0);
            } catch (Exception e) {
                System.out.println("0이라는 숫자를 입력하시면 안 됩니다!!");
            }
        }

        public void b(int num) throws Exception {
            if (num == 0) {
                throw new Exception("num은 0이 될 수 없습니다"); // checked exception 
            }
            
            System.out.println("num: " + num);
        }
    }

    /**
     * Checked Exception을 처리하는 메서드.
     * try-with-resources로 자동 자원 해제.
     */
    public static String queryDatabase(String url, String sql) { // RuntimeException 이구요...
        try (var conn = new DatabaseConnection(url)) {
            return conn.query(sql);
        } catch (IOException e) {
            // Checked → Unchecked 변환 (예외 체이닝)
            throw new CustomException.FileReadException(url, e);
        }
    }

    /**
     * Checked Exception을 throws로 전파.
     * 호출부가 처리 책임을 가짐.
     */
    public static String queryDatabaseChecked(String url, String sql) throws IOException {
        try (var conn = new DatabaseConnection(url)) {
            return conn.query(sql);
        }
    }
}
