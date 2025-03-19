package org.example.services.users.core;

import org.example.models.users.User;
import org.example.models.vehicles.core.Vehicle;
import org.example.repositories.vehicles.IVehicleRepository;
import org.example.services.auth.Authentication;

import java.util.List;

public abstract class UserInterface {
    protected final Authentication authentication;
    protected final IVehicleRepository vehicleRepository;

    public UserInterface(Authentication authentication, IVehicleRepository vehicleRepository) {
        this.authentication = authentication;
        this.vehicleRepository = vehicleRepository;
    }

    public abstract void show();

    protected void showListOfVehicles() {
        System.out.println("Oto lista wszystkich pojazdów:\n");
        for(int i = 1; i < getVehicles().size() + 1; i++)
            System.out.println(i +") "+ getVehicles().get(i-1).toString());
        System.out.println();
    }

    protected List<Vehicle> getVehicles() {
        return vehicleRepository.getVehicles();
    }

    protected User getAuthenticatedUser() {
        return authentication.getAuthenticatedUser();
    }

    protected void logout() {
        System.out.println("\nZostałeś pomyślnie wylogowany.\n");
        authentication.logout();
    }
}
