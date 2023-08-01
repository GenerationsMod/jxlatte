package com.thebombzen.jxlatte.util.functional;

@FunctionalInterface
public interface ExceptionalIntBiConsumer {
    void consumeExceptionally(int x, int y);

    default void consume(int x, int y) {
        try {
            consumeExceptionally(x, y);
        } catch (Throwable ex) {
            FunctionalHelper.sneakyThrow(ex);
        }
    }
}
