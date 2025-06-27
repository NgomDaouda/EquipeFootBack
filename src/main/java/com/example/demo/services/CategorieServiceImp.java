package com.example.demo.services;

import com.example.demo.entities.Categorie;
import com.example.demo.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CategorieServiceImp implements CategorieService{

    @Autowired
    private CategorieRepository categorieRepository;
    @Override
    public Collection<Categorie> getAll() {
        return categorieRepository.findAll();
    }

    @Override
    public Optional<Categorie> getById(Long id) {
        return categorieRepository.findById(id);
    }

    @Override
    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public Categorie update(Long id, Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public void delete(Long id) {
        categorieRepository.deleteById(id);
    }
}
