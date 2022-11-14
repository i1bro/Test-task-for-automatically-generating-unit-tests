package org.jetbrains.person;

import org.jetbrains.car.Car;
import org.jetbrains.car.ElectricCar;
import org.jetbrains.station.ChargingStation;
import org.jetbrains.station.GasStation;
import org.jetbrains.station.Station;
import org.jetbrains.station.StationsPool;
import org.jetbrains.utils.Constants;
import org.jetbrains.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private final int age;
    private final double homeLocation;
    private final double workLocation;
    private Car car;

    public Person(int age, double homeLocation, double workLocation, Car car) {
        this.age = age;
        this.homeLocation = homeLocation;
        this.workLocation = workLocation;

        changeCar(car);
    }

    public void goToWork() {
        drive(workLocation);
    }

    public void goToHome() {
        drive(homeLocation);
    }

    private List<Double> getTripPlan(Car car, double destination) {
        ArrayList<Double> tripPlan = new ArrayList<>();
        if (!car.needsEnergy(destination)) {
            return tripPlan;
        }

        List<Station> compatibleStations = StationsPool.getInstance().getCompatibleStations(car);
        int closestToLocationStationIndex = Utils.lowerBoundStation(compatibleStations, car.getLocation());
        int closestToDestinationStationIndex = Utils.lowerBoundStation(compatibleStations, destination);
        int forAddend;
        if (destination < car.getLocation()) {
            forAddend = -1;
            closestToLocationStationIndex++;
        } else {
            forAddend = 1;
            closestToDestinationStationIndex++;
        }

        double curSimulationPosition = car.getLocation();
        double curSimulationEnergy = car.getEnergyValue();
        int forEndIndex = closestToDestinationStationIndex + forAddend;
        for (int i = closestToLocationStationIndex; i != forEndIndex; ) {
            int lastValidI = i;
            boolean passed = false;
            for(; i != forEndIndex; i += forAddend) {
                if (!Utils.indexInBounds(i, compatibleStations)) {
                    continue;
                }
                if (!Utils.carCanDriveDistance(car, curSimulationPosition - compatibleStations.get(i).getLocation(), curSimulationEnergy)) {
                    break;
                }
                passed = true;
                lastValidI = i;
            }
            if(!passed) {
                if(i == closestToLocationStationIndex) {
                    i += forAddend;
                    continue;
                }
                break;
            }
            curSimulationPosition = compatibleStations.get(lastValidI).getLocation();
            curSimulationEnergy = Constants.MAX_ENERGY;
            tripPlan.add(curSimulationPosition);
        }

        if(Utils.carCanDriveDistance(car,curSimulationPosition - destination, curSimulationEnergy)) {
            return tripPlan;
        } else {
            return null;
        }
    }

    private void drive(double destinationLocation) {
        if (age < Constants.MIN_DRIVING_AGE) {
            System.out.println("This person is too young to drive!");
            return;
        }
        var tripPlan = getTripPlan(car, destinationLocation);
        if(tripPlan == null) {
            System.out.println("Can't drive to this location. Energy usage rate is too high.");
            return;
        }
        if(!tripPlan.isEmpty()) {
            System.out.println("Needs energy");
        }
        for(var stationLocation: tripPlan) {
            addEnergy(stationLocation);
        }
        System.out.println("Drive to " +
                destinationLocation +
                ". Current location " +
                car.getLocation() +
                ". Energy " +
                car.getEnergyValue());
        car.driveTo(destinationLocation);
    }

    private void addEnergy(double destination) {
        System.out.println("Drive to " +
                destination +
                ". Current location " +
                car.getLocation() +
                ". Energy " +
                car.getEnergyValue());
        car.driveTo(destination);
        car.refuel();
    }

    public void changeCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car is empty");
        }
        this.car = car;
    }
}
