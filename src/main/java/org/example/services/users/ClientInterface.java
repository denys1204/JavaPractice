package org.example.services.users;

import org.example.models.users.User;
import org.example.repositories.vehicles.IVehicleRepository;
import org.example.services.auth.Authentication;
import org.example.services.users.core.UserInterface;
import org.example.utiles.ConsoleReader;

public class ClientInterface extends UserInterface {
    public ClientInterface(Authentication authentication, IVehicleRepository vehicleRepository) {
        super(authentication, vehicleRepository);
    }

    @Override
    public void show() {
        User user = getAuthenticatedUser();
        System.out.println("\n\t\t\t\t\tMiło Cię widzieć, " + user.getLogin() + ".\n");
        while(true) {
            showListOfVehicles();
            //System.out.println(user.getRentedVehicleId() < 0 ?
            //        "Na razie nie wynajmujesz żadnych pojazdów." :
            //        ("W tym czasie wynajmujesz pojazd o indeksie " + (user.getRentedVehicleId() + 1) + ".") + "\n");

            System.out.print("Proszę podać indeks pojazdu(-1, aby wylogować się): ");
            int vehicleId = readVehicleIndex();
            if(vehicleId == -1) break;

            System.out.println("\nCo chcesz zrobić z tym pojazdem?\n1) Wynająć\n2) Zwrócić\n3) Powrót do menu");
            int choice = readChoiceIndex();

            makeAction(vehicleId - 1, choice);
        }
    }

    //public boolean rentVehicle(int idx) {
    //    return vehicleRepository.rentVehicle(idx);
    //}
//
    //public boolean returnVehicle(int idx) {
    //    return vehicleRepository.returnVehicle(idx);
    //}

    private int readVehicleIndex() {
        int vehicleId;
        while(true) {
            vehicleId = ConsoleReader.readInt();
            if (vehicleId == -1) {
                logout();
                break;
            }
            if(vehicleId > 0 && vehicleId <= getVehicles().size()) break;
            System.out.println("Proszę podać poprawny indeks.");
        }
        return vehicleId;
    }

    private int readChoiceIndex() {
        int choice;
        while(true) {
            choice = ConsoleReader.readInt();
            if(choice > 0 && choice < 4) break;
            System.out.println("Proszę podać poprawny indeks.");
        }
        return choice;
    }

    private void makeAction(int vehicleIndex, int choice) {
        System.out.println();
        switch (choice) {
            case 1:
                //System.out.println(rentVehicle(vehicleIndex) ?
                //        "Pojazd został wynajęty." :
                //        "Pojazd nie jest dostępny lub już wynajmujesz pojazd.");
                break;
            case 2:
                //System.out.println(returnVehicle(vehicleIndex) ?
                //        "Pojazd został zwrócony." :
                //        "Pojazd nie był wynajęty.");
                break;
            default:
                System.out.println("Powrót do głównego menu.");
                break;
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------\n");
    }
}
