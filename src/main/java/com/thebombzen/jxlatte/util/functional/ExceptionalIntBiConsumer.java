package com.thebombzen.jxlatte.util.functional;

@FunctionalInterface
public interface ExceptionalIntBiConsumer {
    void consumeExceptionally(int x, int y) throws Throwable;

    default void consume(int x, int y) {
        try {
            consumeExceptionally(x, y);
        } catch (Throwable ex) {
            FunctionalHelper.sneakyThrow(ex);
        }
    }
}
