package com.example.demo.repositories;

import com.example.demo.entities.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {
    Optional<Joueur> findByNomAndPrenom(String nom, String prenom); // 👈 ajoute cette méthode

}
