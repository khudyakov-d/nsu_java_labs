package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details;

import java.util.concurrent.atomic.AtomicInteger;

public class EngineDetail extends StorageDetail {
    static AtomicInteger detailNumber = new AtomicInteger(0);

    public EngineDetail() {
        detailNumber.incrementAndGet();
        id = detailNumber.toString();
    }
}
