package com.example.demo.repositories;

import com.example.demo.entities.Paiement;
import com.example.demo.enume.StatutPaiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByStatut(StatutPaiement statut);
    List<Paiement> findByStatutAndDatePaiementBetween(StatutPaiement statut, Date dateDebut, Date dateFin);

}
