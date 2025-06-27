package com.example.demo.services;

import com.example.demo.entities.Coach;
import com.example.demo.repositories.CategorieRepository;
import com.example.demo.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CoachServiceImp implements CoachService{
    @Autowired
    public CoachRepository coachRepository;
    @Autowired
    public CategorieRepository categorieRepository;
    @Override
    public Collection<Coach> getAll() {
        return coachRepository.findAll();
    }

    @Override
    public Optional<Coach> getById(Long id) {
        return coachRepository.findById(id);
    }

    @Override
    public Coach save(Coach coach) {
        return coachRepository.save(coach);
    }

    @Override
    public Coach update(Long id, Coach coach) {
        return coachRepository.save(coach);
    }

    @Override
    public void delete(Long id) {
        coachRepository.deleteById(id);
    }
}
