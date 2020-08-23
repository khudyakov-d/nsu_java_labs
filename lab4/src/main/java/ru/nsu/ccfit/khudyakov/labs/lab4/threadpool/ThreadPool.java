package ru.nsu.ccfit.khudyakov.labs.lab4.threadpool;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ThreadPool implements TaskListener{
    private static final int THREAD_COUNT = 10;

    private final List<ThreadPoolTask> taskQueue;
    private Set<PooledThread> availableThreads;

    @Override
    public void taskStarted(Task t)
    {
        //    System.out.println("Started: "+t.getName());
    }

    @Override
    public void taskFinished(Task t)
    {
        //   System.out.println("Finished: "+t.getName());
    }

    @Override
    public void taskInterrupted(Task t)
    {
        //    System.out.println("Interrupted: " + t.getName());
    }

    public void addTask(Task t)
    {
        addTask(t,this);
    }

    public void addTask(Task t, TaskListener l)
    {
        synchronized (taskQueue)
        {
            taskQueue.add(new ThreadPoolTask(t, l));
            taskQueue.notify();
        }
    }


    public ThreadPool() {

        taskQueue = new LinkedList<>();
        availableThreads = new HashSet<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            availableThreads.add(new PooledThread("Performer_" + i, taskQueue));
        }

        for (PooledThread thread : availableThreads) {
            thread.start();
        }

    }

    public static int getThreadCount() {
        return THREAD_COUNT;
    }

    public List<ThreadPoolTask> getTaskQueue() {
        return taskQueue;
    }
}
