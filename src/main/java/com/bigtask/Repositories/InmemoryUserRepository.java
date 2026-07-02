package com.bigtask.Repositories;

import com.bigtask.User.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InmemoryUserRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        for (User user : users) {
            return users.stream().filter(u -> u.getEmail().equals(email)).findFirst();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }
}
