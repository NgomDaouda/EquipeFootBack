package com.example.demo.services;

import com.example.demo.entities.Comptabilite;
import com.example.demo.entities.Paiement;
import com.example.demo.enume.StatutPaiement;
import com.example.demo.repositories.ComptabiliteRepository;
import com.example.demo.repositories.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class ComptabiliteServiceImp implements ComptabiliteService{

    @Autowired
    private ComptabiliteRepository comptabiliteRepository;
    @Autowired
    private PaiementRepository paiementRepository;
    @Override
    public Collection<Comptabilite> getAll() {
        return comptabiliteRepository.findAll();
    }

    @Override
    public Optional<Comptabilite> getById(Long id) {
        return comptabiliteRepository.findById(id);
    }

    @Override
    public Comptabilite save(Comptabilite comptabilite) {
        return comptabiliteRepository.save(comptabilite);
    }

    @Override
    public Comptabilite update(Comptabilite comptabilite) {
        return comptabiliteRepository.save(comptabilite);
    }

    @Override
    public void deleteById(Long id) {
        comptabiliteRepository.deleteById(id);
    }

   /* public void creerComptabilte() {
        // 1. Obtenir le 1er et le dernier jour du mois courant
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        Date dateDebut = java.sql.Date.valueOf(startOfMonth);
        Date dateFin = java.sql.Date.valueOf(endOfMonth);

        // 2. Récupérer les paiements PAYER du mois courant
        List<Paiement> paiementsPayer = paiementRepository.findByStatutAndDatePaiementBetween(
                StatutPaiement.VALIDER, dateDebut, dateFin);

        // 3. Calculer la somme des salaires mensuels sur la période
        BigDecimal totalSalaireDuMois = paiementsPayer.stream()
                .map(Paiement::getSalaireMensuel)
                .filter(s -> s != null) // pour éviter les nulls
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Copier chaque paiement dans Comptabilite avec totalSalaireDuMois
        for (Paiement paiement : paiementsPayer) {
            Comptabilite comptabilite = new Comptabilite();

            comptabilite.setNom(paiement.getNom());
            comptabilite.setPrenom(paiement.getPrenom());
            comptabilite.setDatePaiement(paiement.getDatePaiement());
            comptabilite.setFonction(paiement.getFonction());
            comptabilite.setSalaireMensuel(paiement.getSalaireMensuel());
            comptabilite.setTotalPaiementMensuel(totalSalaireDuMois); // 💡 valeur identique pour tous

            comptabiliteRepository.save(comptabilite);
        }*/

    /*public void creerComptabilte() {
        List<Paiement> paiementsPayer = paiementRepository.findByStatut(StatutPaiement.VALIDER);
        for (Paiement paiement : paiementsPayer) {
            Comptabilite comptabilite = new Comptabilite();

            comptabilite.setNom(paiement.getNom());
            comptabilite.setPrenom(paiement.getPrenom());
            comptabilite.setDatePaiement(new Date()); // ou paiement.getDatePaiement()
            comptabilite.setFonction(paiement.getFonction());
            comptabilite.setSalaireMensuel(paiement.getSalaireMensuel());
            comptabilite.setTotalPaiementMensuel(paiement.getSalaireMensuel());

            comptabiliteRepository.save(comptabilite);
        }
    }*/

    public void creerComptabilte() {
        List<Paiement> paiementsValidés = paiementRepository.findByStatut(StatutPaiement.VALIDER);

        System.out.println("🔍 Paiements VALIDÉS trouvés : " + paiementsValidés.size());

        // calcul de la somme totale des salaires de ce mois
        BigDecimal totalSalaire = paiementsValidés.stream()
                .map(Paiement::getSalaireMensuel)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        for (Paiement paiement : paiementsValidés) {
            try {
                Comptabilite compta = new Comptabilite();

                compta.setNom(paiement.getNom());
                compta.setPrenom(paiement.getPrenom());
                compta.setDatePaiement(paiement.getDatePaiement());
                compta.setFonction(paiement.getFonction());
                compta.setSalaireMensuel(paiement.getSalaireMensuel());
                compta.setTotalPaiementMensuel(totalSalaire);

                comptabiliteRepository.save(compta);

                System.out.println("✅ Comptabilité ajoutée pour : " + paiement.getNom());
            } catch (Exception e) {
                System.err.println("❌ Erreur pour " + paiement.getNom());
                e.printStackTrace();
            }
        }
    }

}



