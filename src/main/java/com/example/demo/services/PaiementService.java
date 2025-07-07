package com.example.demo.services;

import com.example.demo.entities.Paiement;

import java.util.Collection;
import java.util.Optional;

public interface PaiementService {

    Collection<Paiement> getAll();
    Optional<Paiement> getById(Long id);
    Paiement save(Paiement paiement);
    Paiement update(Paiement paiement);
    void deleteById(Long id);

    boolean validerPaiement(Long id);

    boolean payerSalaire(Long id);
}
