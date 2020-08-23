package ru.nsu.ccfit.khudyakov.labs.lab4.threadpool;

import java.util.List;

public class PooledThread extends Thread {
    private List<ThreadPoolTask> taskQueue;

    public PooledThread(String name, List<ThreadPoolTask> taskQueue) {
        super(name);
        this.taskQueue = taskQueue;
    }

    private void performTask(ThreadPoolTask task) {
        task.prepare();
        try {
            task.execute();
        } catch (InterruptedException ex) {
            task.interrupted();
        }
        task.finish();
    }

    public void run() {
        ThreadPoolTask toExecute = null;

        while (true) {
            synchronized (taskQueue) {
                if (taskQueue.isEmpty()) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException ex) {
                        System.err.println("Thread was inetrrupted:" + getName());
                    }
                    continue;
                } else {
                    toExecute = taskQueue.remove(0);
                }
            }

            //System.out.println(getName() + " got the job: " + toExecute.getName());
            performTask(toExecute);
        }
    }
}