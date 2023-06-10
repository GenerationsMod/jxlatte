package com.thebombzen.jxlatte.util.functional;

@FunctionalInterface
public interface ExceptionalIntConsumer {
    void consume(int a) throws Throwable;
}
