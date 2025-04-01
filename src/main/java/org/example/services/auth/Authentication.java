package org.example.services.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.models.users.User;
import org.example.repositories.users.IUserRepository;
import org.example.utiles.ConsoleReader;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@RequiredArgsConstructor
public class Authentication {
    private static Authentication instance;
    private final IUserRepository repository;
    @Getter
    private User authenticatedUser;

    public static synchronized Authentication getInstance(IUserRepository repository) {
        if (instance == null) instance = new Authentication(repository);
        return instance;
    }

    public User authenticate(String login, String password) {
        Optional<User> user = repository.findByLogin(login);
        if (user.isEmpty() || !BCrypt.checkpw(password ,user.get().getPassword())) return null;
        authenticatedUser = user.get();
        return authenticatedUser;
    }

    public void logout() {
        authenticatedUser = null;
    }

    public User register(User user) {
        return repository.save(user);
    }

    public User registerUser() {
        String login;
        String password;
        String passwordConfirm;

        System.out.println("\t\t\t\t\tProszę się zarejestrować.");
        while (true) {
            System.out.print("Login: ");
            login = ConsoleReader.readString();
            if(repository.findByLogin(login).isEmpty()) break;
            System.out.println("Taki user już istnieje, proszę wprowadzić inny login.\n");
        }
        while (true) {
            System.out.print("Password: ");
            password = ConsoleReader.readString();

            System.out.print("Confirm password: ");
            passwordConfirm = ConsoleReader.readString();
            if (password.equals(passwordConfirm)) break;
            System.out.println("Hasła nie są identyczne, proszę wprowadzić jeszcze raz.\n");
        }
        authenticatedUser = register(User.builder().login(login).password(BCrypt.hashpw(password, BCrypt.gensalt())).role("USER").build());
        return getAuthenticatedUser();
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
