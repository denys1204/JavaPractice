package org.example.repositories.vehicles.impls;

import org.example.models.vehicles.core.Vehicle;
import org.example.repositories.users.IUserRepository;
import org.example.repositories.vehicles.IVehicleRepository;
import org.example.services.auth.Authentication;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.utiles.FileManager.createFileIfNotExists;

public class IVehicleRepositoryImpl implements IVehicleRepository {
    private final String filePath = "src/main/resources/vehicles.csv";
    private final List<Vehicle> vehicles;
    private final IUserRepository userRepository;
    private final Authentication authentication;

    public IVehicleRepositoryImpl(IUserRepository userRepository) throws IOException {
        createFileIfNotExists(filePath);
        this.vehicles = loadFromFile();
        this.userRepository = userRepository;
        this.authentication = Authentication.getInstance(userRepository);
    }

    @Override
    public boolean rentVehicle(int idx) {
        if(vehicles.get(idx).isRented() || authentication.getAuthenticatedUser().getRentedVehicleId()!= -1) return false;
        vehicles.get(idx).setRented(true);
        authentication.getAuthenticatedUser().setRentedVehicleId(idx);
        return userRepository.save() && save();
    }

    @Override
    public boolean returnVehicle(int idx) {
        if(authentication.getAuthenticatedUser().getRentedVehicleId() != idx) return false;
        vehicles.get(idx).setRented(false);
        authentication.getAuthenticatedUser().setRentedVehicleId(-1);
        return userRepository.save() && save();
    }

    @Override
    public List<Vehicle> getVehicles() {
        List<Vehicle> clone_vehicles = new ArrayList<>();
        for(Vehicle vehicle : vehicles) clone_vehicles.add(vehicle.getClone());
        return clone_vehicles;
    }

    @Override
    public boolean save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Vehicle vehicle : vehicles) {
                writer.write(vehicle.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving vehicles.");
            return false;
        }
        return true;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    @Override
    public void removeVehicle(int idx) {
        vehicles.remove(idx);
        save();
    }

    private List<Vehicle> loadFromFile() throws IOException {
        List<Vehicle> vehicles = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length < 6) continue;

            Vehicle vehicle = Vehicle.fromCSV(parts);
            if (vehicle != null) vehicles.add(vehicle);
        }
        reader.close();
        return vehicles;
    }
}
