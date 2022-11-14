package org.jetbrains.utils;

import org.jetbrains.car.Car;
import org.jetbrains.car.ElectricCar;
import org.jetbrains.station.ChargingStation;
import org.jetbrains.station.GasStation;
import org.jetbrains.station.Station;
import org.jetbrains.station.StationsPool;

import java.util.List;

public class Utils {

    public static int lowerBoundStation(List<Station> stations, double location) {
        int leftBorder = -1;
        int rightBorder = stations.size();
        while(rightBorder - leftBorder > 1) {
            int middle = (leftBorder + rightBorder) / 2;
            if(stations.get(middle).getLocation() > location) {
                rightBorder = middle;
            } else {
                leftBorder = middle;
            }
        }
        return leftBorder;
    }

    public static boolean indexInBounds(int index, List<?> list) {
        return index >= 0 && index < list.size();
    }

    public static boolean carCanDriveDistance(Car car, double distance, double energy) {
        return Math.abs(distance) * car.energyUsageRate <= energy;
    }
}
