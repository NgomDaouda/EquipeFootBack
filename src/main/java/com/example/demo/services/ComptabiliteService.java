package com.example.demo.services;

import com.example.demo.entities.Comptabilite;

import java.util.Collection;
import java.util.Optional;

public interface ComptabiliteService {
    Collection<Comptabilite> getAll();
    Optional<Comptabilite> getById(Long id);
    Comptabilite save(Comptabilite comptabilite);
    Comptabilite update(Comptabilite comptabilite);
    void  deleteById(Long id);

    void creerComptabilte();
}
