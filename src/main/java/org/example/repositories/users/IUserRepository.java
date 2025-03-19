package org.example.repositories.users;

import org.example.models.users.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    Optional<User> getUser(String login);
    List<User> getUsers();
    boolean save();
}
