package org.example.services.users;

import org.example.models.users.User;
import org.example.repositories.users.IUserRepository;
import org.example.repositories.vehicles.IVehicleRepository;
import org.example.services.auth.Authentication;
import org.example.services.users.core.UserInterface;
import org.example.utiles.ConsoleReader;

import java.util.List;

public class AdminInterface extends UserInterface {
    private final IUserRepository userRepository;

    public AdminInterface(Authentication authentication, IVehicleRepository vehicleRepository, IUserRepository userRepository) {
        super(authentication, vehicleRepository);
        this.userRepository = userRepository;
    }

    @Override
    public void show() {
        User admin = getAuthenticatedUser();
        System.out.println("\n\t\t\t\t\tWelcome to the admin panel, " + admin.getUsername() + ".\n");

        while (true) {
            System.out.println("1) Lista użytkowników\n2) Lista pojazdów");
            System.out.print("Proszę podać indeks(-1, aby wylogować się): ");

            int choice = readChoiceIndex();
            System.out.println();
            if (choice == 1) {
                System.out.println("Oto lista wszystkich userów, oprócz adminów:\n");
                for(int i = 1; i < getAllUsersExceptAdmins().size() + 1; i++) {
                    User user = getAllUsersExceptAdmins().get(i-1);
                    System.out.println(i +") "+ user);
                    System.out.println("Wynajmowany pojazd: " + getRentedVehicle(user.getRentedVehicleId()) + "\n");
                }
                System.out.println("--------------------------------------------------------------------------------------------------------------------------------\n");
            } else if (choice == 2) {
                showListOfVehicles();
                System.out.println("\n1) Dodać pojazd\n2) Usunąć pojazd\n3) Powrót do menu");
                System.out.print("Co zrobimy?: ");
                choice = readChoiceIndex();
                System.out.println();
                if(choice == 1) {
                    System.out.println("Proszę podać typ pojazdu: ");
                    
                } else if(choice == 2) {
                    int vehicleId = readVehicleIndex();
                    vehicleRepository.removeVehicle(vehicleId);
                    System.out.println("Pojazd o indeksie " + vehicleId + " został usunięty\n");
                } else if(choice == 3) {
                    System.out.println("Powrót do głównego menu.");
                }
            } else if (choice == -1) {
                logout();
            }
            if (choice == -1) break;
        }
    }

    public List<User> getAllUsersExceptAdmins() {
        return userRepository.getUsers().stream()
                .filter(user -> !user.getRole().equals("admin")).toList();
    }

    private String getRentedVehicle(int idx) {
        return idx == -1 ? "null" : getVehicles().get(idx).toString();
    }

    private int readChoiceIndex() {
        int choice;
        while(true) {
            choice = ConsoleReader.readInt();
            if(choice > 0 && choice < 3) break;
            System.out.println("Proszę podać poprawny indeks.");
        }
        return choice;
    }

    private int readVehicleIndex() {
        int vehicleId;
        while(true) {
            vehicleId = ConsoleReader.readInt();
            if (vehicleId == -1) break;
            if(vehicleId > 0 && vehicleId <= getVehicles().size()) break;
            System.out.println("Proszę podać poprawny indeks.");
        }
        return vehicleId;
    }
}
