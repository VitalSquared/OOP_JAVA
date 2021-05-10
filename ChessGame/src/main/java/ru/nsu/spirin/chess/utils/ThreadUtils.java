package ru.nsu.spirin.chess.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public final class ThreadUtils {
    private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public static void submitThread(Runnable thread) {
        executor.submit(thread);
    }

    public static void shutdown() {
        executor.shutdownNow();
    }
}
