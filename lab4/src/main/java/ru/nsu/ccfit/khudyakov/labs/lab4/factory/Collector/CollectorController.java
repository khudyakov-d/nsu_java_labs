package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Collector;

public class CollectorController implements Runnable {
    private Collector collector;

    public CollectorController(Collector collector) {
        this.collector = collector;
    }

    @Override
    public void run() {
        try {
            while (true) {
                collector.getCarStorage().isEmpty();
                collector.collectCar();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
