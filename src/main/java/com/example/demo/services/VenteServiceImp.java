package com.example.demo.services;

import com.example.demo.dto.SeniorDto;
import com.example.demo.dto.VenteDto;
import com.example.demo.entities.Senior;
import com.example.demo.entities.Vente;
import com.example.demo.repositories.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class VenteServiceImp implements VenteService{
    @Autowired
    private VenteRepository venteRepository;
    @Override
    public Collection<Vente> getAll() {
        return venteRepository.findAll();
    }

    @Override
    public Optional<Vente> getById(Long id) {
        return venteRepository.findById(id);
    }

    @Override
    public Vente save(Vente vente) {
        return venteRepository.save(vente);
    }

    @Override
    public Vente update(Vente vente) {
        return venteRepository.save(vente);
    }

    @Override
    public void delete(Long id) {
        venteRepository.findById(id);
    }

    public Vente enregistrerVente(VenteDto venteDto) {
        Vente vente = new Vente();

        vente.setNom(venteDto.getNom());
        vente.setPrenom(venteDto.getPrenom());

        // Calcul de l'âge à partir de la date de naissance
        if (venteDto.getDateNaissance() != null) {
            LocalDate naissance = venteDto.getDateNaissance().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            int age = Period.between(naissance, LocalDate.now()).getYears();
            vente.setAge(age);
        } else {
            vente.setAge(null); // ou 0 ou une exception selon ta logique métier
        }

        vente.setDateNaissance(venteDto.getDateNaissance());
        vente.setDateVente(LocalDate.now());
        vente.setPoste(venteDto.getPoste());
        vente.setPiedFort(venteDto.getPiedFort());
        vente.setStatut(venteDto.getStatut());
        vente.setNationalite(venteDto.getNationalite());
        vente.setPrixVent(venteDto.getPrixVent());

        return venteRepository.save(vente);
    }


}
