package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Storages;

import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Suppliers.DetailsSupplier;

import java.util.LinkedList;

public class Storage<T> {
    private LinkedList<T> details;
    private final int CAPACITY;
    private int currentOccupancy;
    private int totalProduced;
    private int delay;
    private Class<T> clazzOfT;
    volatile private LinkedList<DetailsSupplier<T>> suppliers;


    public Storage(int CAPACITY, int delay, Class<T> classOfT) {
        this.delay = delay;
        this.clazzOfT = classOfT;
        this.CAPACITY = CAPACITY;
        this.currentOccupancy = 0;
        this.totalProduced = 0;
        this.details = new LinkedList<>();
        this.suppliers = new LinkedList<>();

        LinkedList list = new LinkedList();
        LinkedList list2 = list;

    }

    public void add() throws InterruptedException, IllegalAccessException, InstantiationException {
        synchronized (this) {
            while (currentOccupancy >= CAPACITY) {
                wait();
            }

            details.add(clazzOfT.newInstance());
            currentOccupancy++;
            totalProduced++;
            notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (details.size() == 0) {
                wait();
            }

            T value = details.removeFirst();
            currentOccupancy--;

            notifyAll();

            return value;
        }
    }

    public void addSupplier() {
        synchronized (this) {
            DetailsSupplier<T> detailsSupplier = new DetailsSupplier<>(this, delay);
            suppliers.add(detailsSupplier);
            new Thread(detailsSupplier).start();
        }
    }


    synchronized public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    synchronized public int getTotalProduced() {
        return totalProduced;
    }

    synchronized public void setDelay(int delay) {
        this.delay = delay;
        for (DetailsSupplier<T> supplier : suppliers) {
            supplier.setDelay(this.delay);
        }
    }
}
