package org.example.repositories.users.impls;

import org.example.models.users.User;
import org.example.repositories.users.IUserRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.utiles.FileManager.createFileIfNotExists;

public class IUserRepositoryImpl implements IUserRepository {
    private final String filePath = "src/main/resources/users.csv";
    private final List<User> users;

    public IUserRepositoryImpl() throws IOException {
        createFileIfNotExists(filePath);
        this.users = loadFromFile();
    }

    @Override
    public Optional<User> getUser(String login) {
        return users.stream().filter(user -> user.getUsername().equals(login)).findFirst();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public boolean save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (User user : users) {
                writer.write(user.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving vehicles.");
            return false;
        }
        return true;
    }

    private List<User> loadFromFile() throws IOException {
        List<User> usersFromFile = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(";");
            if (parts.length < 4) continue;
            usersFromFile.add(User.fromCSV(parts));
        }
        reader.close();
        return usersFromFile;
    }
}
