package org.example.chapter05_4;

/**
 * ISP 적용 예시들.
 * 각 장치는 자신이 지원하는 인터페이스만 구현한다.
 */
public class MultiFunctionDevice {

    // ----------------------------------------------------------------
    // ISP 위반 — 모든 기능을 하나의 인터페이스에
    // ----------------------------------------------------------------
    interface BadMultiFunctionDevice {
        void print(String doc);
        void scan(String doc);
        void fax(String doc);
        void copy(String doc);
    }

    // 단순 프린터 — scan/fax/copy가 불필요하지만 구현 강제
    static class BadSimplePrinter implements BadMultiFunctionDevice {
        @Override public void print(String doc) { System.out.println("[BadPrinter] 출력: " + doc); }
        @Override public void scan(String doc)  { throw new UnsupportedOperationException("스캔 미지원"); }
        @Override public void fax(String doc)   { throw new UnsupportedOperationException("팩스 미지원"); }
        @Override public void copy(String doc)  { throw new UnsupportedOperationException("복사 미지원"); }
    }

    // ----------------------------------------------------------------
    // ISP 적용 — 단순 프린터
    // ----------------------------------------------------------------
    static class SimplePrinter implements Printable {
        @Override
        public void print(String document) {
            System.out.println("[SimplePrinter] 출력: " + document);
        }
    }

    // ISP 적용 — 스캐너
    static class Scanner implements Scannable {
        @Override
        public void scan(String document) {
            System.out.println("[Scanner] 스캔: " + document);
        }
    }

    // ISP 적용 — 팩스 능력
    interface Faxable {
        void fax(String document);
    }

    // ISP 적용 — 복합기 (여러 인터페이스 조합)
    static class AllInOnePrinter implements Printable, Scannable, Faxable {
        @Override
        public void print(String document) {
            System.out.println("[AllInOne] 출력: " + document);
        }

        @Override
        public void scan(String document) {
            System.out.println("[AllInOne] 스캔: " + document);
        }

        @Override
        public void fax(String document) {
            System.out.println("[AllInOne] 팩스 전송: " + document);
        }
    }
}
