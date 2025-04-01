package org.example.repositories.vehicles.impls;

import com.google.gson.reflect.TypeToken;
import org.example.models.vehicles.Vehicle;
import org.example.repositories.vehicles.IVehicleRepository;
import org.example.utiles.JsonFileStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class IVehicleRepositoryImpl implements IVehicleRepository {
    private final String filePath = "vehicles.json";
    private final List<Vehicle> vehicles;
    private final JsonFileStorage<Vehicle> storage;

    public IVehicleRepositoryImpl() {
        this.storage = new JsonFileStorage<>(filePath, new TypeToken<List<Vehicle>>(){}.getType());
        this.vehicles = new ArrayList<>(storage.load());
    }

    @Override
    public List<Vehicle> findAll() {
        return this.vehicles;
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        return vehicles.stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getId() == null || vehicle.getId().isBlank()) {
            vehicle.setId(UUID.randomUUID().toString());
        } else {
            deleteById(vehicle.getId());
        }
        vehicles.add(vehicle);
        storage.save(vehicles);
        return vehicle;
    }

    @Override
    public void deleteById(String id) {
        vehicles.removeIf(vehicle -> vehicle.getId().equals(id));
        storage.save(this.vehicles);
    }
}
