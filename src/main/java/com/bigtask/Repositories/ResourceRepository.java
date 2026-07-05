package com.bigtask.Repositories;

import com.bigtask.Resource.Resource;

import java.util.List;
import java.util.Optional;

public interface ResourceRepository {
    void add(Resource resource);
    Optional<Resource> findByName(String name);
    List<Resource> findAll();
}
