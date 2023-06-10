package com.thebombzen.jxlatte.util.functional;

import java.util.function.Supplier;

@FunctionalInterface
public interface ExceptionalSupplier<U> extends Supplier<U> {
    
    U supplyExceptionally() throws Throwable;

    default U get() {
        try {
            return supplyExceptionally();
        } catch (Throwable ex) {
            return FunctionalHelper.sneakyThrow(ex);
        }
    }
}
