package org.example.chapter05_4;

public class IspMain {

    static void printDocument(Printable printer, String doc) {
        printer.print(doc);
    }

    static void scanDocument(Scannable scanner, String doc) {
        scanner.scan(doc);
    }

    public static void main(String[] args) {
        demonstrateViolation();
        demonstrateISP();
    }

    static void demonstrateViolation() {
        System.out.println("=== ISP 위반 ===");

        MultiFunctionDevice.BadSimplePrinter bad = new MultiFunctionDevice.BadSimplePrinter();
        bad.print("보고서.pdf");

        try {
            bad.scan("사진.jpg");
        } catch (UnsupportedOperationException e) {
            System.out.println("예외: " + e.getMessage());
        }
        System.out.println("→ 단순 프린터가 scan/fax/copy 구현을 강제당함\n");
    }

    static void demonstrateISP() {
        System.out.println("=== ISP 적용 ===");

        var simplePrinter = new MultiFunctionDevice.SimplePrinter();
        var scanner       = new MultiFunctionDevice.Scanner();
        var allInOne      = new MultiFunctionDevice.AllInOnePrinter();

        // 각 장치는 자신이 지원하는 인터페이스만 구현
        printDocument(simplePrinter, "보고서.pdf");
        printDocument(allInOne, "계획서.docx");

        scanDocument(scanner, "사진.jpg");
        scanDocument(allInOne, "계약서.pdf");

        allInOne.fax("견적서.pdf");

        // 클라이언트는 필요한 인터페이스에만 의존
        Printable p = allInOne;  // Printable만 필요한 클라이언트
        Scannable s = allInOne;  // Scannable만 필요한 클라이언트
        p.print("메모.txt");
        s.scan("영수증.pdf");

        System.out.println("→ 각 클라이언트가 필요한 인터페이스에만 의존");
    }
}
