package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Collector;

import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.AccessoryDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.BodyDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.EngineDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Storages.CarStorage;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Storages.Storage;
import ru.nsu.ccfit.khudyakov.labs.lab4.threadpool.ThreadPool;


public class Collector {
    private Storage<BodyDetail> bodyStorage;
    private Storage<EngineDetail> engineStorage;
    private Storage<AccessoryDetail> accessoryStorage;
    private CarStorage carStorage;

    private ThreadPool threadPool;


    public Collector(Storage<BodyDetail> bodyStorage, Storage<EngineDetail> engineStorage,
                     Storage<AccessoryDetail> accessoryStorage, CarStorage carStorage) {
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
        this.accessoryStorage = accessoryStorage;
        this.carStorage = carStorage;
        threadPool = new ThreadPool();
    }


    public void collectCar() {
        if (threadPool.getTaskQueue().size() < 1000) {
            threadPool.addTask(new CollectorTask(this));
        }
    }


    public Storage<BodyDetail> getBodyStorage() {
        return bodyStorage;
    }

    public Storage<EngineDetail> getEngineStorage() {
        return engineStorage;
    }

    public Storage<AccessoryDetail> getAccessoryStorage() {
        return accessoryStorage;
    }

    public CarStorage getCarStorage() {
        return carStorage;
    }


}
