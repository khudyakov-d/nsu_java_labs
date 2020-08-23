package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Main;

import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Collector.Collector;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Collector.CollectorController;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Dealers.CarSale;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.AccessoryDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.BodyDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.EngineDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Storages.CarStorage;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Storages.Storage;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.UI.UserInterface;

public class Main {
    public static void main(String[] args) {
        ConfigParser parser = new ConfigParser();

        Storage<BodyDetail> bodyStorage = new Storage<>(parser.getBodyStorageSize(),
                parser.getBodySupplierMinDelay(),
                BodyDetail.class);
        bodyStorage.addSupplier();


        Storage<EngineDetail> engineStorage = new Storage<>(parser.getEngineStorageSize(),
                parser.getEngineSupplierMinDelay(),
                EngineDetail.class);
        engineStorage.addSupplier();

        Storage<AccessoryDetail> accessoryStorage = new Storage<>(parser.getAccessoryStorageSize(),
                parser.getAccessorySupplierMinDelay(),
                AccessoryDetail.class);
        for (int i = 0; i < parser.getAccessorySuppliers(); i++) {
            accessoryStorage.addSupplier();
        }

        CarStorage carStorage = new CarStorage(parser.getCarStorageSize());
        Collector collector = new Collector(bodyStorage, engineStorage, accessoryStorage, carStorage);
        CarSale carSale = new CarSale(carStorage, parser.getDealers(), parser.getDealerMinDelay());

        new Thread(new UserInterface(collector,carSale, parser)).start();
        new Thread(new CollectorController(collector)).start();
        new Thread(carSale).start();
    }
}
