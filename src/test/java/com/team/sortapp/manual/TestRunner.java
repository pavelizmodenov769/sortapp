package com.team.sortapp.manual;

public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    private TestRunner() {
    }

    public static void reset() {
        passed = 0;
        failed = 0;
    }

    public static void check(boolean condition, String message) {
        if (condition) {
            passed++;
        } else {
            failed++;
            System.out.println("   [FAIL] " + message);
        }
    }

    public static void checkEquals(Object expected, Object actual, String message) {
        boolean ok = java.util.Objects.equals(expected, actual);
        check(ok, ok ? message : message + " (ожидалось <" + expected +
                                 ">, получено <" + actual + ">)");
    }

    public static void summary() {
        System.out.println(" Пройдено: " + passed + ", провалено: " + failed);
        if (failed > 0) {
            System.out.println("  >>>ТЕСТЫ ПРОВАЛЕНЫ<<<");
        } else {
            System.out.println("   >>>ТЕСТЫ ПРОЙДЕНЫ<<<");
        }
    }

    public static boolean hasFailures() {
        return failed > 0;
    }
}