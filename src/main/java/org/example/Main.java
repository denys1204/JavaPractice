package org.example;

import org.example.models.users.User;
import org.example.repositories.users.IUserRepository;
import org.example.repositories.users.impls.IUserRepositoryImpl;
import org.example.repositories.vehicles.IVehicleRepository;
import org.example.repositories.vehicles.impls.IVehicleRepositoryImpl;
import org.example.services.auth.Authentication;
import org.example.services.users.AdminInterface;
import org.example.services.users.ClientInterface;
import org.example.services.users.core.UserInterface;
import org.example.utiles.ConsoleReader;

public class Main {
    public static void main(String[] args)  {
        IUserRepository userRepository = new IUserRepositoryImpl();
        IVehicleRepository vehicleRepository = new IVehicleRepositoryImpl();
        Authentication authentication = Authentication.getInstance(userRepository);

        while(true) {
            System.out.println("Login or Register?");
            String choice = ConsoleReader.readString();
            User user = choice.equals("Login") ? authentication.authUser() : choice.equals("Register") ? authentication.registerUser() : null;
            if (user == null) {
                System.out.println("Bad string\n");
                continue;
            }
            UserInterface ui = user.getRole().equals("ADMIN") ?
                    new AdminInterface(authentication, vehicleRepository, userRepository) :
                    new ClientInterface(authentication, vehicleRepository);
            ui.show();
        }
    }
}