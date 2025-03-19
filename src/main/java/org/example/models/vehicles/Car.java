package org.example.models.vehicles;

import org.example.models.vehicles.core.Vehicle;

public class Car extends Vehicle {
    public Car(String brand, String model, Integer year, Double price, boolean rented, String type) {
        super(brand, model, year, price, rented, type);
    }

    @Override
    public Car getClone() {
        return new Car(this.getBrand(),
                this.getModel(),
                this.getYear(),
                this.getPrice(),
                this.isRented(),
                this.getType());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    public static Car createCar(String brand, String model, Integer year, Double price, boolean rented, String type) {
        return new Car(brand, model, year, price, rented, type);
    }
}
