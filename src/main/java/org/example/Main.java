package org.example;

import org.example.repositories.users.IUserRepository;
import org.example.repositories.users.impls.IUserRepositoryImpl;
import org.example.repositories.vehicles.IVehicleRepository;
import org.example.repositories.vehicles.impls.IVehicleRepositoryImpl;
import org.example.services.auth.Authentication;
import org.example.services.users.AdminInterface;
import org.example.services.users.ClientInterface;
import org.example.services.users.core.UserInterface;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        IUserRepository userRepository = new IUserRepositoryImpl();
        IVehicleRepository vehicleRepository = new IVehicleRepositoryImpl(userRepository);
        Authentication authentication = Authentication.getInstance(userRepository);

        while(true) {
            UserInterface ui = authentication.authUser().getRole().equals("admin") ?
                    new AdminInterface(authentication, vehicleRepository, userRepository) :
                    new ClientInterface(authentication, vehicleRepository);
            ui.show();
        }
    }
}