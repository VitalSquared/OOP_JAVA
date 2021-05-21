package ru.nsu.spirin.chess.thread;

import java.util.ArrayList;
import java.util.List;

public final class ThreadPool {
    public static final ThreadPool INSTANCE = new ThreadPool(4, 4);

    private final int maxThreads;
    private final BlockingQueue<Runnable> queue;
    private final List<Thread>            threads;
    private final List<TaskExecutor> taskExecutors;

    public ThreadPool(int queueSize, int maxThreads) {
        this.maxThreads = maxThreads;
        this.queue = new BlockingQueue<>(queueSize);
        this.threads = new ArrayList<>();
        this.taskExecutors = new ArrayList<>();
    }

    public void submitTask(Runnable task) {
        try {
            if (!anyFreeThreads() && taskExecutors.size() < this.maxThreads) {
                TaskExecutor executor = new TaskExecutor(queue);
                Thread thread = new Thread(executor);
                System.out.println("Created thread: " + thread.getName());
                taskExecutors.add(executor);
                threads.add(thread);
                thread.start();
            }
            queue.enqueue(task);
        }
        catch (Exception ignored) {}
    }

    public void shutdown() {
        for (var thread : threads) {
            thread.interrupt();
        }
    }

    private boolean anyFreeThreads() {
        if (taskExecutors.size() == 0) return false;
        for (var executor : taskExecutors) {
            if (executor.isFree()) return true;
        }
        return false;
    }
}