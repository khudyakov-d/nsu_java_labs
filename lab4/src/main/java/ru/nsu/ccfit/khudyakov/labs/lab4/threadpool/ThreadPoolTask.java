package ru.nsu.ccfit.khudyakov.labs.lab4.threadpool;

public class ThreadPoolTask {
    private TaskListener listener;
    private Task task;

    public ThreadPoolTask(Task task, TaskListener listener) {
        this.listener = listener;
        this.task = task;
    }

    void prepare() {
        listener.taskStarted(task);
    }

    void finish() {
        listener.taskFinished(task);
    }

    void interrupted() {
        listener.taskInterrupted(task);
    }

    void execute() throws InterruptedException {
        task.performWork();
    }

    String getName() {
        return task.getName();
    }

}
