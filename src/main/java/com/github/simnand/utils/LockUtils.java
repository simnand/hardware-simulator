package com.github.simnand.utils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.locks.Lock;
import java.util.function.Supplier;

public class LockUtils {

    public static void withLock(@NotNull Lock lock, @NotNull Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    public static <T> T withLock(@NotNull Lock lock, @NotNull Supplier<T> supplier) {
        lock.lock();
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }
}
