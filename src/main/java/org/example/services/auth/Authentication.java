package org.example.services.auth;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.models.users.User;
import org.example.repositories.users.IUserRepository;
import org.example.utiles.ConsoleReader;

import java.util.Optional;

public class Authentication {
    private static Authentication instance;
    private final IUserRepository repository;
    private User authenticatedUser;

    private Authentication(IUserRepository repository) {
        this.repository = repository;
    }

    public static synchronized Authentication getInstance(IUserRepository repository) {
        if (instance == null) instance = new Authentication(repository);
        return instance;
    }

    public User authenticate(String login, String password) {
        Optional<User> user = repository.getUser(login);
        if (user.isEmpty() || !user.get().getPassword().equals(DigestUtils.sha256Hex(password))) return null;
        authenticatedUser = user.get();
        return authenticatedUser;
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }

    public void logout() {
        authenticatedUser = null;
    }

    public User authUser() {
        System.out.println("\t\t\t\t\tProszę się zalogować.");
        while(true) {
            System.out.print("Login: ");
            String username = ConsoleReader.readString();
            System.out.print("Password: ");
            String password = ConsoleReader.readString();
            User user = authenticate(username, password);
            if(user != null) return user;
            System.out.println("\nProszę podać poprawne dane.");
        }
    }
}
