package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Storages;

import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.AccessoryDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.BodyDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.Car;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.EngineDetail;

import java.util.LinkedList;

public class CarStorage {
    private final int CAPACITY;
    private LinkedList<Car> cars;
    private int currentOccupancy;
    private int totalProduced;

    public CarStorage(int CAPACITY) {
        this.totalProduced = 0;
        this.currentOccupancy = 0;
        this.CAPACITY = CAPACITY;
        cars = new LinkedList<>();
    }


    public void addCar(BodyDetail bodyDetail, EngineDetail engineDetail, AccessoryDetail accessoryDetail)
            throws InterruptedException {
        synchronized (this) {

            while (currentOccupancy >= CAPACITY) {
                wait();
            }

            cars.add(new Car(bodyDetail, engineDetail, accessoryDetail));
            currentOccupancy++;
            totalProduced++;
            notifyAll();
        }
    }

    public Car getCar() throws InterruptedException {
        synchronized (this) {
            while (currentOccupancy == 0) {
                wait();
            }

            Car car = cars.removeFirst();
            currentOccupancy--;

            notifyAll();

            return car;
        }
    }

    public void isEmpty() throws InterruptedException {
        synchronized (this) {
            while (currentOccupancy == CAPACITY) {
                wait();
            }
        }
    }

    public int getCurrentOccupancy() {
        return currentOccupancy;
    }

    public int getTotalProduced() {
        return totalProduced;
    }

    public int getCAPACITY() {
        return CAPACITY;
    }


}
