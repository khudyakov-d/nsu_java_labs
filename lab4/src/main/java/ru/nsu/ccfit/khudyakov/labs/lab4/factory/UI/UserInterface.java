package ru.nsu.ccfit.khudyakov.labs.lab4.factory.UI;

import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Collector.Collector;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Dealers.CarSale;
import ru.nsu.ccfit.khudyakov.labs.lab4.factory.Main.ConfigParser;

import javax.swing.*;
import java.awt.*;

public class UserInterface extends JFrame implements Runnable {
    private Collector collector;
    private ConfigParser configParser;
    private CarSale carSale;
    private final int DELAY = 3;
    private final int LABELS_COUNT = 10;

    public UserInterface(Collector collector, CarSale carSale, ConfigParser configParser) {
        this.collector = collector;
        this.carSale = carSale;
        this.configParser = configParser;
    }

    private JSlider makeSlider(int min, int max, int current) {
        JSlider slider = new JSlider(min, max, current);
        slider.setOrientation(JSlider.VERTICAL);
        slider.setMajorTickSpacing(min);
        slider.setPaintLabels(true);
        slider.setPreferredSize(new Dimension(50, 200));
        return slider;
    }

    private Container makeSliderContainer(JSlider slider, JLabel label) {
        Container container = new Container();
        container.setLayout(new BorderLayout());
        container.add(slider, BorderLayout.NORTH);
        container.add(label, BorderLayout.SOUTH);

        return container;
    }

    private JLabel makeSliderLabel(String name) {
        JLabel label = new JLabel(name);
        label.setPreferredSize(new Dimension(50, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

    private JLabel makeInfoLabel(String name) {
        JLabel label = new JLabel(name);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(150, 50));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

    @Override
    public void run() {
        setMinimumSize(new Dimension(600, 250));
        setTitle("Config");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        Container infoContainer = new Container();
        Container slidersContainer = new Container();
        Container currentOccupancyContainer = new Container();
        Container totalProducedContainer = new Container();

        slidersContainer.setLayout(new FlowLayout(FlowLayout.CENTER));
        currentOccupancyContainer.setLayout(new BoxLayout(currentOccupancyContainer, BoxLayout.Y_AXIS));
        totalProducedContainer.setLayout(new BoxLayout(totalProducedContainer, BoxLayout.Y_AXIS));
        infoContainer.setLayout(new BoxLayout(infoContainer, BoxLayout.Y_AXIS));


        int bodyDelay = configParser.getBodySupplierMinDelay();
        JSlider bodySupplierDelaySlider = makeSlider(bodyDelay, bodyDelay * LABELS_COUNT, bodyDelay);
        slidersContainer.add(makeSliderContainer(bodySupplierDelaySlider, makeSliderLabel("Body")));

        int engineDelay = configParser.getEngineSupplierMinDelay();
        JSlider engineSupplierDelaySlider = makeSlider(engineDelay, engineDelay * LABELS_COUNT, engineDelay);
        slidersContainer.add(makeSliderContainer(engineSupplierDelaySlider, makeSliderLabel("Engine")));

        int accessoryDelay = configParser.getAccessorySupplierMinDelay();
        JSlider accessorySupplierDelaySlider = makeSlider(accessoryDelay, accessoryDelay * LABELS_COUNT, accessoryDelay);
        slidersContainer.add(makeSliderContainer(accessorySupplierDelaySlider, makeSliderLabel("Acc-ry")));

        int dealerDelay = configParser.getDealerMinDelay();
        JSlider dealerDelaySlider = makeSlider(dealerDelay, dealerDelay * LABELS_COUNT, dealerDelay);
        slidersContainer.add(makeSliderContainer(dealerDelaySlider, makeSliderLabel("Dealer")));

        JLabel currentBodyOccupancy = makeInfoLabel("Body: " + collector.getBodyStorage().getCurrentOccupancy());
        JLabel currentEngineOccupancy = makeInfoLabel("Engine: " + collector.getEngineStorage().getCurrentOccupancy());
        JLabel currentAccessoryOccupancy = makeInfoLabel("Accessory: " + collector.getAccessoryStorage().getCurrentOccupancy());
        JLabel currentCarOccupancy = makeInfoLabel("Cars: " + collector.getCarStorage().getCurrentOccupancy());

        currentOccupancyContainer.add(makeInfoLabel("Storage occupancy: "));
        currentOccupancyContainer.add(currentBodyOccupancy);
        currentOccupancyContainer.add(currentEngineOccupancy);
        currentOccupancyContainer.add(currentAccessoryOccupancy);
        currentOccupancyContainer.add(currentCarOccupancy);

        JLabel bodyTotalProduced = makeInfoLabel("Body: " + collector.getBodyStorage().getTotalProduced());
        JLabel engineTotalProduced = makeInfoLabel("Engine: " + collector.getEngineStorage().getTotalProduced());
        JLabel accessoryTotalProduced = makeInfoLabel("Accessory: " + collector.getAccessoryStorage().getTotalProduced());
        JLabel carTotalProduced = makeInfoLabel("Cars: " + collector.getCarStorage().getTotalProduced());

        totalProducedContainer.add(makeInfoLabel("Total Produced"));
        totalProducedContainer.add(bodyTotalProduced);
        totalProducedContainer.add(engineTotalProduced);
        totalProducedContainer.add(accessoryTotalProduced);
        totalProducedContainer.add(carTotalProduced);

//        infoContainer.add(makeInfoLabel("Count of: "));
//
//        infoContainer.add(makeInfoLabel("Suppliers: "));
//        infoContainer.add(makeInfoLabel("Body: " + collector.getBodyStorage().getSuppliers().size()));
//        infoContainer.add(makeInfoLabel("Engine: " + collector.getEngineStorage().getSuppliers().size()));
//        infoContainer.add(makeInfoLabel("Accessory: " + collector.getAccessoryStorage().getSuppliers().size()));
//
//        infoContainer.add(makeInfoLabel("Other: "));
//        infoContainer.add(makeInfoLabel("Workers: " + configParser.getWorkers()));
//        infoContainer.add(makeInfoLabel("Dealers: " + configParser.getDealers()));
//
//
//        this.add(infoContainer);

        this.add(slidersContainer);
        this.add(currentOccupancyContainer);
        this.add(totalProducedContainer);

        bodySupplierDelaySlider.addChangeListener(e -> {
            int value = ((JSlider) e.getSource()).getValue();
            collector.getBodyStorage().setDelay(value);
        });

        engineSupplierDelaySlider.addChangeListener(e -> {
            int value = ((JSlider) e.getSource()).getValue();
            collector.getEngineStorage().setDelay(value);
        });

        accessorySupplierDelaySlider.addChangeListener(e -> {
            int value = ((JSlider) e.getSource()).getValue();
            collector.getAccessoryStorage().setDelay(value);
        });

        dealerDelaySlider.addChangeListener(e -> {
            int value = ((JSlider) e.getSource()).getValue();
            carSale.setDealersDelay(value);
        });

        new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    currentBodyOccupancy.setText("Body: " + collector.getBodyStorage().getCurrentOccupancy());
                    currentEngineOccupancy.setText("Engine: " + collector.getEngineStorage().getCurrentOccupancy());
                    currentAccessoryOccupancy.setText("Accessory: " + collector.getAccessoryStorage().getCurrentOccupancy());
                    currentCarOccupancy.setText("Cars: " + collector.getCarStorage().getCurrentOccupancy());


                    bodyTotalProduced.setText("Body: " + collector.getBodyStorage().getTotalProduced());
                    engineTotalProduced.setText("Engine: " + collector.getEngineStorage().getTotalProduced());
                    accessoryTotalProduced.setText("Accessory: " + collector.getAccessoryStorage().getTotalProduced());
                    carTotalProduced.setText("Cars: " + collector.getCarStorage().getTotalProduced());

                    Thread.sleep(DELAY);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        pack();
        setVisible(true);
    }
}
