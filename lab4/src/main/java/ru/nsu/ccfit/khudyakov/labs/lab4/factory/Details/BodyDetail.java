package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Details;

import java.util.concurrent.atomic.AtomicInteger;

public class BodyDetail extends StorageDetail {
    private static AtomicInteger detailNumber = new AtomicInteger(0);

    public BodyDetail() {
        detailNumber.incrementAndGet();
        id = detailNumber.toString();
    }
}
