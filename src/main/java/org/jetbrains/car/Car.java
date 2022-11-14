package org.jetbrains.car;

import org.jetbrains.utils.Constants;

public abstract class Car {
    protected double location;
    private final Energy energy;
    private final double energyUsageRate;
    protected double energyThreshold;

    public Car(double location, double energyUsageRate) {
        this.location = location;

        if (energyUsageRate <= 0) {
            throw new IllegalArgumentException("energy usage rate should be higher than 0.");
        }
        this.energyUsageRate = energyUsageRate;
        energy = new Energy();
    }

    public boolean needsEnergy(double destination) {
        double distance = Math.abs(destination - this.location);
        double estimatedUsage = distance * energyUsageRate;
        return (this.energy.getEnergy() - estimatedUsage <= this.energyThreshold);
    }

    public void driveTo(double destination) {
        double distance = Math.abs(destination - this.location);
        this.energy.reduceEnergy(distance * energyUsageRate);
        this.location = destination;
    }

    public void refuel() {
        System.out.println("Refueling");
        this.energy.recharge();
    }

    public double getLocation() {
        return location;
    }

    public double getEnergyValue() {
        return (this.energy.getEnergy());
    }

    protected static class Energy {
        private double energy;

        public Energy() {
            energy = Constants.MAX_ENERGY;
        }

        public void reduceEnergy(double value) {
            energy -= value;
            if(energy < 0) {
                throw new OutOfEnergyException(energy);
            }
        }

        public double getEnergy() {
            return energy;
        }

        public void recharge() {
            energy = Constants.MAX_ENERGY;
        }
    }
}
