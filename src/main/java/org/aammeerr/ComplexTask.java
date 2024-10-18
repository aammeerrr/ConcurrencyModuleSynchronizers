package org.aammeerr;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class ComplexTask {
    private final int taskId;

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public int execute() {

        System.out.println("Thread: " +  Thread.currentThread().getName()+ "started executing task");

        int result = ThreadLocalRandom.current().nextInt(1000);

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000,3000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Thread: " +  Thread.currentThread().getName()+ "COMPLETED task");
        return result;
    }
}
