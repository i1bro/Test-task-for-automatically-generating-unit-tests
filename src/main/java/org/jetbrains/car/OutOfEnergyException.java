package org.jetbrains.car;

public class OutOfEnergyException extends RuntimeException {
    public OutOfEnergyException(Double energy) {
        super("Energy value is " + energy.toString() + ", which is below 0");
    }
}
