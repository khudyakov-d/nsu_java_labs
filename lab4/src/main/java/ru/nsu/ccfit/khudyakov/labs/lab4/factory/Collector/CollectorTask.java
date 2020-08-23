package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Collector;

import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.AccessoryDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.BodyDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details.EngineDetail;
import ru.nsu.ccfit.khudyakov.labs.lab4.threadpool.Task;

public class CollectorTask implements Task {
    Collector collector;

    public CollectorTask(Collector collector) {
        this.collector = collector;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void performWork() throws InterruptedException {

        BodyDetail bodyDetail = collector.getBodyStorage().poll();
        EngineDetail engineDetail = collector.getEngineStorage().poll();
        AccessoryDetail accessoryDetail = collector.getAccessoryStorage().poll();

        collector.getCarStorage().addCar(bodyDetail, engineDetail, accessoryDetail);
    }
}

