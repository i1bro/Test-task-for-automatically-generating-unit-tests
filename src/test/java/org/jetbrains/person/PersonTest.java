package org.jetbrains.person;

import org.jetbrains.car.Car;
import org.jetbrains.car.ElectricCar;
import org.jetbrains.car.PetrolCar;
import org.jetbrains.utils.Constants;
import org.jetbrains.utils.TestConstants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonTest {

    static Random random;

    @BeforeAll
    static void initRandom() {
        random = new Random(TestConstants.TESTS_RANDOM_SEED);
    }

    @BeforeEach
    void printSeparator() {
        System.out.println("\n--------------------------------------------\n");
    }

    @Test
    void testEmptyCar() {
        Car car = new ElectricCar(Constants.MIN_LOCATION, 1);
        Person person = new Person(Constants.MIN_DRIVING_AGE, Constants.MIN_LOCATION, Constants.MAX_LOCATION, car);
        assertThrows(IllegalArgumentException.class, () -> {
            new Person(Constants.MIN_DRIVING_AGE, Constants.MAX_LOCATION, Constants.MIN_LOCATION, null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            person.changeCar(null);
        });
    }

    @Test
    void testUnderageDriver() {
        Car car = new ElectricCar(Constants.MIN_LOCATION, 1);
        Person person = new Person(0, Constants.MIN_LOCATION, Constants.MAX_LOCATION, car);
        person.goToWork();
        assert (car.getLocation() == Constants.MIN_LOCATION);
    }

    @Test
    void testMultipleStops() {
        Car car = new ElectricCar(Constants.MIN_LOCATION, 5);
        Person person = new Person(Constants.MIN_DRIVING_AGE, Constants.MIN_LOCATION, Constants.MAX_LOCATION, car);
        person.goToWork();
        assert (car.getLocation() == Constants.MAX_LOCATION);
        assert (car.getEnergyValue() > Constants.ELECTRIC_CAR_ENERGY_THRESHOLD);
        assert (car.getEnergyValue() <= Constants.MAX_ENERGY);
    }

    @Test
    void testImpossibleRoute() {
        Car car = new ElectricCar(Constants.MIN_LOCATION, 10);
        Person person = new Person(Constants.MIN_DRIVING_AGE, Constants.MIN_LOCATION, Constants.MAX_LOCATION, car);
        person.goToWork();
        assert (car.getLocation() == Constants.MIN_LOCATION);
        assert (car.getEnergyValue() == Constants.MAX_ENERGY);
    }

    @Test
    void testChangeCar() {
        Car electricCar = new ElectricCar(Constants.MIN_LOCATION, 1);
        Car petrolCar = new PetrolCar(Constants.MAX_LOCATION, 1);
        Person person = new Person(Constants.MIN_DRIVING_AGE, Constants.MIN_LOCATION, Constants.MAX_LOCATION, electricCar);
        person.goToWork();
        assert (electricCar.getLocation() == Constants.MAX_LOCATION);
        person.changeCar(petrolCar);
        person.goToHome();
        assert (petrolCar.getLocation() == Constants.MIN_LOCATION);
        assert (electricCar.getLocation() == Constants.MAX_LOCATION);
    }

    @Test
    void testPerson() {
        Car car = new PetrolCar(10, 2);
        Person person = new Person(19, 10.1, 46.10, car);
        person.goToWork();
        person.goToHome();
        person.goToWork();
        person.goToHome();
        person.goToWork();

        assert (car.getEnergyValue() > 0 && car.getEnergyValue() <= 100);
    }

    double generateRandomPosition() {
        return random.nextDouble() * (Constants.MAX_LOCATION - Constants.MIN_LOCATION) + Constants.MIN_LOCATION;
    }

    double generateRandomEnergyUsage() {
        double value = 0;
        while (value == 0) {
            value = random.nextDouble();
        }
        return value * TestConstants.MAX_ENERGY_USAGE;
    }

    @RepeatedTest(TestConstants.NUMBER_OF_ITERATIONS)
    void randomTest() {
        double energyUsage = generateRandomEnergyUsage();
        double homeLocation = generateRandomPosition();
        double workLocation = generateRandomPosition();
        Car car = new PetrolCar(generateRandomPosition(), energyUsage);
        Person person = new Person(Constants.MIN_DRIVING_AGE, homeLocation, workLocation, car);
        for (int i = 0; i < TestConstants.NUMBER_OF_EVENTS; i++) {
            int roll = random.nextInt(8);
            if (roll == 0) {
                energyUsage = generateRandomEnergyUsage();
                car = new PetrolCar(generateRandomPosition(), energyUsage);
                person.changeCar(car);
            } else if (roll == 1) {
                homeLocation = generateRandomPosition();
                workLocation = generateRandomPosition();
                person = new Person(Constants.MIN_DRIVING_AGE, homeLocation, workLocation, car);
            } else {
                double curLocation = car.getLocation();
                double curEnergy = car.getEnergyValue();
                if (roll % 2 == 0) {
                    person.goToWork();
                    if (curLocation != workLocation && curLocation == car.getLocation()) {
                        assert (curEnergy == car.getEnergyValue());
                    } else {
                        assert (car.getLocation() == workLocation);
                    }
                } else {
                    person.goToHome();
                    if (curLocation != homeLocation && curLocation == car.getLocation()) {
                        assert (curEnergy == car.getEnergyValue());
                    } else {
                        assert (car.getLocation() == homeLocation);
                    }
                }
                if (energyUsage < 2) {
                    assert (car.getEnergyValue() > Constants.PETROL_CAR_ENERGY_THRESHOLD);
                }
            }
        }
    }

}