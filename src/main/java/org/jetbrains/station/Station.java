package org.jetbrains.station;

public abstract class Station {
    private final double location;

    public Station(int location) {
        this.location = location;
    }

    public double getLocation() {
        return location;
    }
}
