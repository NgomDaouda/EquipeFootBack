package com.example.demo.services;

import com.example.demo.entities.Categorie;
import com.example.demo.entities.Joueur;

import java.util.Collection;
import java.util.Optional;

public interface CategorieService {
    Collection<Categorie> getAll();
    Optional<Categorie> getById(Long id);
    Categorie save(Categorie categorie);
    Categorie update(Long id,Categorie categorie);
    void delete(Long id);
}
