package org.example.models.vehicles;

import org.example.models.vehicles.core.Vehicle;

import java.util.Objects;

public class Motorcycle extends Vehicle {
    private String category;

    public Motorcycle(String brand, String model, Integer year, Double price, boolean rented, String type, String category) {
        super(brand, model, year, price, rented, type);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
    //public void setCategory(String category) {
    //    this.category = category;
    //}

    @Override
    public String toCSV() {
        return super.toCSV() + ";" + category;
    }

    @Override
    public String toString() {
        return super.toString() + ", category='" + category + "'";
    }

    @Override
    public Motorcycle getClone() {
        return new Motorcycle(this.getBrand(),
                this.getModel(),
                this.getYear(),
                this.getPrice(),
                this.isRented(),
                this.getType(),
                this.getCategory());
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && Objects.equals(category, ((Motorcycle) o).category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), category);
    }

    public static Motorcycle createMotorcycle(String brand, String model, Integer year, Double price, boolean rented, String type, String category) {
        return new Motorcycle(brand, model, year, price, rented, type, category);
    }
}
