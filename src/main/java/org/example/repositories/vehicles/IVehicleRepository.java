package org.example.repositories.vehicles;

import org.example.models.vehicles.core.Vehicle;

import java.io.IOException;
import java.util.List;

public interface IVehicleRepository {
    boolean rentVehicle(int idx);
    boolean returnVehicle(int idx);
    List<Vehicle> getVehicles();
    boolean save() throws IOException;
    void addVehicle(Vehicle vehicle);
    void removeVehicle(int idx);
}
