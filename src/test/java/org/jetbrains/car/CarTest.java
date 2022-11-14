package org.jetbrains.car;

import org.jetbrains.utils.Constants;
import org.jetbrains.utils.TestConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.jetbrains.utils.TestConstants.EPS;
import static org.junit.jupiter.api.Assertions.*;


public class CarTest {
    static Random random;

    @BeforeAll
    static void initTests() {
        random = new Random(TestConstants.TESTS_RANDOM_SEED);
    }

    @Test
    void testCarCreation() {
        Car electricCar = new ElectricCar(Constants.MIN_LOCATION, 1);
        Car petrolCar = new PetrolCar(Constants.MIN_LOCATION, 1);
        assertEquals(electricCar.energyThreshold, Constants.ELECTRIC_CAR_ENERGY_THRESHOLD);
        assertEquals(petrolCar.energyThreshold, Constants.PETROL_CAR_ENERGY_THRESHOLD);
        assertEquals(electricCar.getEnergyValue(), Constants.MAX_ENERGY);
        assertEquals(petrolCar.getEnergyValue(), Constants.MAX_ENERGY);
    }

    @Test
    void testCarCreationIllegal() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ElectricCar(Constants.MIN_LOCATION, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new PetrolCar(Constants.MIN_LOCATION, 0);
        });
    }

    @Test
    void testCarDriveRefuel() {
        Car car = new ElectricCar(Constants.MIN_LOCATION, 2.0);
        double destination = Constants.MIN_LOCATION + Constants.MAX_ENERGY / 2.0;
        assert(car.needsEnergy(destination));
        car.driveTo(destination);
        assert(car.getLocation() == destination);
        assert(car.getEnergyValue() < EPS);
        car.refuel();
        assert(car.getEnergyValue() == Constants.MAX_ENERGY);
    }
}
