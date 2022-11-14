package org.jetbrains.station;

import org.jetbrains.car.Car;
import org.jetbrains.utils.Constants;

import java.util.ArrayList;
import java.util.List;

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
        // Add charging stations
        chargingStations.add(new ChargingStation(15));
        chargingStations.add(new ChargingStation(35));
        chargingStations.add(new ChargingStation(47));
        chargingStations.add(new ChargingStation(59));
        chargingStations.add(new ChargingStation(70));
        chargingStations.add(new ChargingStation(86));
        chargingStations.add(new ChargingStation(96));
    }

    public ChargingStation getClosestChargingStation(Car car) {
        return (ChargingStation) getClosestStation(car, this.chargingStations);
    }

    public GasStation getClosestGasStation(Car car) {
        return (GasStation) getClosestStation(car, this.gasStations);
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
}
