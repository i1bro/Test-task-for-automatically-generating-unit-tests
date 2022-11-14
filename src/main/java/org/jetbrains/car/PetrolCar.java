package org.jetbrains.car;

import org.jetbrains.utils.Constants;

public class PetrolCar extends Car {
    public PetrolCar(double location, double energyUsageRate) {
        super(location, energyUsageRate);
        energyThreshold = Constants.PETROL_CAR_ENERGY_THRESHOLD;
    }
}
