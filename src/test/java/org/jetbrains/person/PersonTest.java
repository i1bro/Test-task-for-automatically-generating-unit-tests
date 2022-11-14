package org.jetbrains.person;

import org.jetbrains.car.Car;
import org.jetbrains.car.ElectricCar;
import org.jetbrains.car.PetrolCar;
import org.jetbrains.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonTest {

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
        assert(car.getLocation() == Constants.MIN_LOCATION);
    }

    @Test
    void testMultipleStops() {
        Car car = new ElectricCar(Constants.MIN_LOCATION, 5);
        Person person = new Person(Constants.MIN_DRIVING_AGE, Constants.MIN_LOCATION, Constants.MAX_LOCATION, car);
        person.goToWork();
        assert(car.getLocation() == Constants.MAX_LOCATION);
        assert(car.getEnergyValue() > Constants.ELECTRIC_CAR_ENERGY_THRESHOLD);
        assert(car.getEnergyValue() <= Constants.MAX_ENERGY);
    }

    @Test
    void testImpossibleRoute() {
        Car car = new ElectricCar(Constants.MIN_LOCATION, 10);
        Person person = new Person(Constants.MIN_DRIVING_AGE, Constants.MIN_LOCATION, Constants.MAX_LOCATION, car);
        person.goToWork();
        assert(car.getLocation() == Constants.MIN_LOCATION);
        assert(car.getEnergyValue() == Constants.MAX_ENERGY);
    }

    @Test
    void testChangeCar() {
        Car electricCar = new ElectricCar(Constants.MIN_LOCATION, 1);
        Car petrolCar = new PetrolCar(Constants.MAX_LOCATION, 1);
        Person person = new Person(Constants.MIN_DRIVING_AGE, Constants.MIN_LOCATION, Constants.MAX_LOCATION, electricCar);
        person.goToWork();
        assert(electricCar.getLocation() == Constants.MAX_LOCATION);
        person.changeCar(petrolCar);
        person.goToHome();
        assert(petrolCar.getLocation() == Constants.MIN_LOCATION);
        assert(electricCar.getLocation() == Constants.MAX_LOCATION);
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

}