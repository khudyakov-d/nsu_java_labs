package ru.nsu.ccfit.khudyakov.labs.lab4.threadpool;

public interface Task {
    String getName();
    public void performWork() throws InterruptedException;
}

