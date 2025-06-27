package com.example.demo.services;

import com.example.demo.entities.Vente;

import java.util.Collection;
import java.util.Optional;

public interface VenteService {
    Collection<Vente> getAll();
    Optional<Vente> getById(Long id);
    Vente save(Vente vente);
    Vente update(Vente vente);
    void delete(Long id);
}
