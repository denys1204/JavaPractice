package org.example.repositories.users.impls;

import com.google.gson.reflect.TypeToken;
import org.example.models.users.User;
import org.example.repositories.users.IUserRepository;
import org.example.utiles.JsonFileStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class IUserRepositoryImpl implements IUserRepository {
    private final String filePath = "users.json";
    private final List<User> users;
    private final JsonFileStorage<User> storage;

    public IUserRepositoryImpl() {
        this.storage = new JsonFileStorage<>(filePath, new TypeToken<List<User>>(){}.getType());
        this.users = new ArrayList<>(storage.load());
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return users.stream().filter(user -> user.getLogin().equals(login)).findFirst();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId().isBlank()) {
            user.setId(UUID.randomUUID().toString());
        } else {
            deleteById(user.getId());
        }
        users.add(user);
        storage.save(users);
        return user;
    }

    @Override
    public void deleteById(String id) {
        if(users.removeIf(user -> user.getId().equals(id))) storage.save(users);
    }
}
