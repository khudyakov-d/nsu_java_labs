package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details;

import java.util.concurrent.atomic.AtomicInteger;

public class Car extends StorageDetail {
    private static AtomicInteger detailNumber = new AtomicInteger(0);
    private BodyDetail bodyDetail;
    private EngineDetail engineDetail;
    private AccessoryDetail accessoryDetail;

    public Car(BodyDetail bodyDetail, EngineDetail engineDetail, AccessoryDetail accessoryDetail) {
        this.bodyDetail = bodyDetail;
        this.engineDetail = engineDetail;
        this.accessoryDetail = accessoryDetail;

        detailNumber.incrementAndGet();
        id = detailNumber.toString();
    }

    public BodyDetail getBodyDetail() {
        return bodyDetail;
    }

    public EngineDetail getEngineDetail() {
        return engineDetail;
    }

    public AccessoryDetail getAccessoryDetail() {
        return accessoryDetail;
    }
}
