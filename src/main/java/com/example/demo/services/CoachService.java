package com.example.demo.services;

import com.example.demo.entities.Coach;

import java.util.Collection;
import java.util.Optional;

public interface CoachService {
    Collection<Coach> getAll();
    Optional<Coach>getById(Long id);
    Coach save(Coach coach);
    Coach update(Long id, Coach coach);
    void delete(Long id);
}
