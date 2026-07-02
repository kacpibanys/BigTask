package com.bigtask.Repositories;

import com.bigtask.Resource.Resource;
import com.bigtask.User.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryResourceRepository implements ResourceRepository {
    private final List<Resource> resources = new ArrayList<>();


    @Override
    public void add(Resource resource) {
        resources.add(resource);
    }

    @Override
    public Optional<Resource> findByName(String name) {
        for (Resource resource : resources) {
            return resources.stream().filter(r -> r.getName().equals(name)).findFirst();
        }
        return Optional.empty();
    }

    @Override
    public List<Resource> findAll() {
        return new ArrayList<>(resources);
    }
}