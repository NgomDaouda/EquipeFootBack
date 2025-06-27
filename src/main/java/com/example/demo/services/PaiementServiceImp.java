package com.example.demo.services;

import com.example.demo.entities.Paiement;
import com.example.demo.entities.Salaire;
import com.example.demo.repositories.PaiementRepository;
import com.example.demo.repositories.SalaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaiementServiceImp implements PaiementService{

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private SalaireRepository salaireRepository;

    @Override
    public Collection<Paiement> getAll() {
        return paiementRepository.findAll();
    }

    @Override
    public Optional<Paiement> getById(Long id) {
        return paiementRepository.findById(id);
    }

    @Override
    public Paiement save(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement update(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public void deleteById(Long id) {
        paiementRepository.deleteById(id);
    }
/*Exécution le 1er de chaque mois à 00h01
    @Scheduled(cron = "0 1 0 1 * *") // (minute heure jour mois jourSemaine année)*/
//    @Scheduled(cron = "0 */10 * * * *") // Toutes les 10 minutes
//    public void genererPaiementsMensuels() {
//        List<Salaire> salaires = salaireRepository.findAll();
//
//        for (Salaire salaire : salaires) {
//            Paiement paiement = new Paiement();
//            paiement.setNom(salaire.getNom());
//            paiement.setPrenom(salaire.getPrenom());
//            paiement.setSalaireMensuel(salaire.getSalaireMensuel() != null ? salaire.getSalaireMensuel() : BigDecimal.ZERO);
//            paiement.setDatePaiement(new Date()); // aujourd'hui
//            paiement.setFonction(salaire.getFonction());
//           // paiement.setSalaire(salaire);
//
//            paiementRepository.save(paiement);
//        }
//
//        System.out.println("💰 Paiements générés automatiquement à " + new Date());
//    }

}
