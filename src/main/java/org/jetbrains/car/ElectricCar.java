package org.jetbrains.car;

import org.jetbrains.utils.Constants;

public class ElectricCar extends Car {
    public ElectricCar(double location, double energyUsageRate) {
        super(location, energyUsageRate);
        energyThreshold = Constants.ELECTRIC_CAR_ENERGY_THRESHOLD;
    }
}
