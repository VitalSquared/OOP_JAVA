package ru.nsu.spirin.chess.thread;

import java.util.ArrayList;
import java.util.List;

public final class ThreadPool {
    public static final ThreadPool INSTANCE = new ThreadPool(4, 4);

    private final BlockingQueue<Runnable> queue;
    private final List<Thread>            threads;

    public ThreadPool(int queueSize, int nThread) {
        this.queue = new BlockingQueue<>(queueSize);
        this.threads = new ArrayList<>();
        TaskExecutor task;
        for (int count = 0; count < nThread; count++) {
            task = new TaskExecutor(queue);
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }
    }

    public void submitTask(Runnable task) {
        try {
            queue.enqueue(task);
        }
        catch (Exception ignored) {}
    }

    public void shutdown() {
        for (var thread : threads) {
            thread.interrupt();
        }
    }
}