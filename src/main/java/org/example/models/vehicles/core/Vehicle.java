package org.example.models.vehicles.core;

import org.example.models.vehicles.Car;
import org.example.models.vehicles.Motorcycle;

import java.util.Objects;

public abstract class Vehicle {
    private String brand;
    private String model;
    private Integer year;
    private Double price;
    private boolean rented;
    private String type;

    public Vehicle(String brand, String model, Integer year, Double price, boolean rented, String type) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.rented = rented;
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public Integer getYear() {
        return year;
    }
    public Double getPrice() {
        return price;
    }
    public boolean isRented() {
        return rented;
    }
    public String getType() {
        return type;
    }

    //public void setBrand(String brand) {
    //    this.brand = brand;
    //}
    //public void setModel(String model) {
    //    this.model = model;
    //}
    //public void setYear(Integer year) {
    //    this.year = year;
    //}
    //public void setPrice(Double price) {
    //    this.price = price;
    //}
    public void setRented(boolean rented) {
        this.rented = rented;
    }
    //public void setType(String type) {
    //    this.type = type;
    //}

    public String toCSV() {
        return brand + ";" + model + ";" + year + ";" + price + ";" + rented + ";" + type;
    }

    public static Vehicle fromCSV(String[] parts) {
        String brand = parts[0];
        String model = parts[1];
        Integer year = Integer.parseInt(parts[2]);
        Double price = Double.parseDouble(parts[3]);
        boolean rented = Boolean.parseBoolean(parts[4]);
        String type = parts[5];

        return switch (type) {
            case "Car" -> new Car(brand, model, year, price, rented, type);
            case "Motorcycle" -> new Motorcycle(brand, model, year, price, rented, type, parts[6]);
            default -> null;
        };
    }

    @Override
    public String toString() {
        return "brand='" + brand + "'" +
                ", model='" + model + "'" +
                ", year=" + year +
                ", price=" + price +
                ", rented=" + rented +
                ", type='" + type + "'";
    }

    public abstract Vehicle getClone();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return rented == vehicle.rented &&
                Objects.equals(brand, vehicle.brand) &&
                Objects.equals(model, vehicle.model) &&
                Objects.equals(year, vehicle.year) &&
                Objects.equals(price, vehicle.price) &&
                Objects.equals(type, vehicle.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, year, price, rented, type);
    }
}
