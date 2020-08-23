package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Dealers;

import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Storages.CarStorage;

import java.util.LinkedList;

public class CarSale implements Runnable {
    private final int DEALER_COUNT;
    private int dealersDelay;
    private CarStorage carStorage;
    private LinkedList<Dealer> dealers;

    public CarSale(CarStorage carStorage, int DEALER_COUNT, int dealersDelay) {
        this.DEALER_COUNT = DEALER_COUNT;
        this.dealersDelay = dealersDelay;
        this.carStorage = carStorage;
        this.dealers = new LinkedList<>();
    }

    @Override
    public void run() {
        for (int i = 0; i < DEALER_COUNT; i++) {
            Dealer dealer = new Dealer(carStorage, dealersDelay);
            dealers.add(dealer);
            new Thread(dealer, String.valueOf(i)).start();
        }
    }

    public void setDealersDelay(int dealersDelay) {
        this.dealersDelay = dealersDelay;

        for (Dealer dealer: dealers){
            dealer.setDelay(dealersDelay);
        }
    }
}
