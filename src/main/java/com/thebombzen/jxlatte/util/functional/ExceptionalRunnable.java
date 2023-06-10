package com.thebombzen.jxlatte.util.functional;

@FunctionalInterface
public interface ExceptionalRunnable extends Runnable {
    void runExceptionally() throws Throwable;

    @Override
    default void run() {
        try {
            runExceptionally();
        } catch (Throwable ex) {
            FunctionalHelper.sneakyThrow(ex);
        }
    }

    static Runnable of(ExceptionalRunnable r) {
        return r;
    }

    default <T> ExceptionalSupplier<T> supply() {
        return () -> {
            run();
            return null;
        };
    }
}
