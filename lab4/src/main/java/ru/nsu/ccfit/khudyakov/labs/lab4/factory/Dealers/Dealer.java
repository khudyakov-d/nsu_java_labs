package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Dealers;

import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.Car;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Storages.CarStorage;


public class Dealer implements Runnable {
    private int delay;
    private CarStorage carStorage;

    public Dealer(CarStorage carStorage, int delay) {
        this.delay = delay;
        this.carStorage = carStorage;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Car car = carStorage.getCar();

                System.out.println(
                        "Dealer " + Thread.currentThread().getName() + ":"
                                + "Car " + car.getId() + "("
                                + "Body " + car.getBodyDetail().getId() + ","
                                + "Engine " + car.getEngineDetail().getId() + ","
                                + "Accessory " + car.getAccessoryDetail().getId() + ")");

                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void setDelay(int delay) {
        this.delay = delay;
    }
}
