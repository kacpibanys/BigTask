package com.bigtask.Repositories;

import com.bigtask.User.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void addUser(User user);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
