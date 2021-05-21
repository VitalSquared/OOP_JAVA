package ru.nsu.spirin.chess.thread;

public final class TaskExecutor implements Runnable {
    private final BlockingQueue<Runnable> queue;
    private volatile boolean isFree;

    public TaskExecutor(BlockingQueue<Runnable> queue) {
        this.queue = queue;
        this.isFree = true;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String name = Thread.currentThread().getName();
                Runnable task = queue.dequeue();
                System.out.println("Task Started by Thread: " + name);
                this.isFree = false;
                task.run();
                this.isFree = true;
                System.out.println("Task Finished by Thread: " + name);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isFree() {
        return this.isFree;
    }
}
