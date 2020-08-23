package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Suppliers;

import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Storages.Storage;


public class DetailsSupplier<T> implements Runnable {
    private Storage<T> storage;
    volatile private int delay;

    public DetailsSupplier(Storage<T> storage, int delay) {
        this.delay = delay;
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                storage.add();
                Thread.sleep(delay);
            }
        } catch (InterruptedException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    synchronized public void setDelay(int delay) {
        this.delay = delay;
    }
}
