package org.aammeerr;

import java.util.concurrent.*;

public class ComplexTaskExecutor {

    private final int numberOfTasks;
    private final ExecutorService executorService;
    private final CyclicBarrier barrier;
    private final int[] results;


    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
        this.executorService = Executors.newFixedThreadPool(numberOfTasks);
        this.barrier = new CyclicBarrier(numberOfTasks, this::mergeResults);
        this.results = new int[numberOfTasks];
    }

    public void executeTasks(int numberOfTasks) {
        for (int i = 0; i < numberOfTasks; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                ComplexTask task = new ComplexTask(taskId);
                int result = task.execute();
                results[taskId] = result;

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    private void mergeResults() {
        int total = 0;
        for (int result : results) {
            total += result;
        }
        System.out.println("All tasks are completed. Merged result: " + total);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
