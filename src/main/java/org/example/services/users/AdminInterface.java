package org.example.services.users;

import org.example.models.users.User;
import org.example.models.vehicles.Car;
import org.example.models.vehicles.Motorcycle;
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
            System.out.print("\nProszę podać indeks(-1, aby wylogować się): ");

            int choice = readChoiceIndex();
            System.out.println();
            if (choice == -1) {
                logout();
                break;
            }

            if (choice == 1) {
                System.out.println("Oto lista wszystkich userów, oprócz adminów:\n");

                for (int i = 1; i < getAllUsersExceptAdmins().size() + 1; i++) {
                    User user = getAllUsersExceptAdmins().get(i - 1);
                    System.out.println(i + ") " + user);
                    System.out.println("Wynajmowany pojazd: " + getRentedVehicle(user.getRentedVehicleId()) + "\n");
                }

                System.out.println("--------------------------------------------------------------------------------------------------------------------------------\n");
            } else if (choice == 2) {
                showListOfVehicles();

                System.out.println("\n1) Dodać pojazd\n2) Usunąć pojazd");
                System.out.print("\nProszę podać indeks(-1, aby się cofnąć do menu): ");

                choice = readChoiceIndex();
                System.out.println();

                if (choice == 1) {
                    System.out.println("Proszę podać typ pojazdu: ");
                    String type = readVehicleType();
                    switch (type) {
                        case "Car":
                            addCar();
                            System.out.println("Samochód został pomyślnie dodany.");
                            break;
                        case "Motorcycle":
                            addMotorcycle();
                            System.out.println("Motor został pomyślnie dodany.");
                            break;
                    }
                } else if (choice == 2) {
                    System.out.print("Proszę podać indeks pojazdu: ");
                    int vehicleId = readVehicleIndex();
                    if (vehicleId == -1) {
                        System.out.println("\nPowrót do głównego menu.\n");
                        continue;
                    }
                    System.out.println();
                    if (vehicleRepository.getVehicles().get(vehicleId - 1).isRented()) {
                        System.out.println("Nie można usunąć tego pojazdu z powodu wypożyczenia.");
                    } else {
                        vehicleRepository.removeVehicle(vehicleId - 1);
                        System.out.println("Pojazd o indeksie " + vehicleId + " został usunięty.\n");
                    }
                }
                else if (choice == -1)
                    System.out.println("Powrót do głównego menu.\n");
            }
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
            if (choice == -1) break;
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

    private String readVehicleType() {
        String vehicleType;
        while (true) {
            vehicleType = ConsoleReader.readString();
            switch (vehicleType) {
                case "Car":
                    return "Car";
                case "Motorcycle":
                    return "Motorcycle";
                default:
                    System.out.println("Nie znany typ pojazdu, proszę wprowadzić ponownie. ");
            }
        }
    }

    private void addCar() {
        System.out.println("Proszę podać dane samochodu: ");

        System.out.print("1) Brand: ");
        String brand = ConsoleReader.readString();

        System.out.print("\n2) Model: ");
        String model = ConsoleReader.readString();

        System.out.print("\n3) Year: ");
        int year = ConsoleReader.readInt();

        System.out.print("\n4) Price: ");
        double price = ConsoleReader.readDouble();
        System.out.println();

        vehicleRepository.addVehicle(Car.createCar(brand, model, year, price, false,"Car"));
    }

    private void addMotorcycle() {
        System.out.println("Proszę podać dane motoru: ");

        System.out.print("1) Brand: ");
        String brand = ConsoleReader.readString();

        System.out.print("\n2) Model: ");
        String model = ConsoleReader.readString();

        System.out.print("\n3) Year: ");
        int year = ConsoleReader.readInt();

        System.out.print("\n4) Price: ");
        double price = ConsoleReader.readDouble();

        System.out.print("\n5) Category: ");
        String category = ConsoleReader.readString();
        System.out.println();

        vehicleRepository.addVehicle(Motorcycle.createMotorcycle(brand, model, year, price, false, "Motorcycle", category));
    }
}
