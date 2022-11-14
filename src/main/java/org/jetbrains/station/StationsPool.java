package org.jetbrains.station;

import org.jetbrains.car.Car;
import org.jetbrains.car.ElectricCar;
import org.jetbrains.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.binarySearch;
import static java.util.Collections.sort;

public class StationsPool {
    private static StationsPool stations;
    private final List<Station> gasStations = new ArrayList<>();
    private final List<Station> chargingStations = new ArrayList<>();

    public static StationsPool getInstance() {
        if (stations == null) {
            stations = new StationsPool();
        }
        return stations;
    }

    private StationsPool() {
        // Add gas stations
        gasStations.add(new GasStation(10));
        gasStations.add(new GasStation(25));
        gasStations.add(new GasStation(45));
        gasStations.add(new GasStation(67));
        gasStations.add(new GasStation(77));
        gasStations.add(new GasStation(89));
        gasStations.add(new GasStation(97));
        gasStations.sort(Comparator.comparingDouble(Station::getLocation));
        // Add charging stations
        chargingStations.add(new ChargingStation(15));
        chargingStations.add(new ChargingStation(35));
        chargingStations.add(new ChargingStation(47));
        chargingStations.add(new ChargingStation(59));
        chargingStations.add(new ChargingStation(70));
        chargingStations.add(new ChargingStation(86));
        chargingStations.add(new ChargingStation(96));
        chargingStations.sort(Comparator.comparingDouble(Station::getLocation));
    }

    public ChargingStation getClosestChargingStation(Car car) {
        return (ChargingStation) getClosestStation(car, chargingStations);
    }

    public GasStation getClosestGasStation(Car car) {
        return (GasStation) getClosestStation(car, gasStations);
    }

    private Station getClosestStation(Car car, List<Station> stations) {
        double minDestination = Constants.MAX_LOCATION;
        Station closestChargingStation = null;
        for (Station chargingStation : stations) {
            double destination = Math.abs(car.getLocation() - chargingStation.getLocation());
            if (destination < minDestination) {
                closestChargingStation = chargingStation;
                minDestination = destination;
            }
        }
        return closestChargingStation;
    }

    public List<Station> getCompatibleStations(Car car) {
        List<Station> compatibleStations;
        if(car instanceof ElectricCar) {
            compatibleStations = chargingStations;
        } else {
            compatibleStations = gasStations;
        }
        return Collections.unmodifiableList(compatibleStations);
    }


}
