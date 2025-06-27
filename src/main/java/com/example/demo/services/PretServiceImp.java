package com.example.demo.services;

import com.example.demo.dto.JoueurDto;
import com.example.demo.dto.PretDto;
import com.example.demo.entities.Pret;
import com.example.demo.repositories.PretRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PretServiceImp implements PretService{

    @Autowired
    private PretRepository pretRepository;
    @Override
    public Collection<Pret> getAll() {
        return pretRepository.findAll();
    }

    @Override
    public Optional<Pret> getById(Long id) {
        return pretRepository.findById(id);
    }

    @Override
    public Pret save(Pret pret) {
        return pretRepository.save(pret);
    }

    @Override
    public Pret update(Pret pret) {
        return pretRepository.save(pret);
    }

    @Override
    public void delete(Long id) {
        pretRepository.deleteById(id);
    }

    @Override
    public PretDto savePret( PretDto dto) {
        Pret pret = new Pret();

        pret.setNom(dto.getNom());
        pret.setPrenom(dto.getPrenom());
        pret.setDatePret(dto.getDatePret());
        pret.setDateNaissance(dto.getDateNaissance());
        pret.setStatut(dto.getStatut());
        pret.setNationalite(dto.getNationalite());
        pret.setClubConserne(dto.getClubConserne());
        pret.setPhoto(dto.getPhoto());
        pret.setTypePhoto(dto.getTypePhoto());

        // Calcul de l'âge
        if (dto.getDateNaissance() != null) {
            LocalDate naissance = dto.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int age = Period.between(naissance, LocalDate.now()).getYears();
            pret.setAge(age);
        }

        Pret saved = pretRepository.save(pret);

        dto.setId(saved.getId());
        return dto;
    }


    @Override
    public void uploadPhoto(Long pretId, MultipartFile file) throws IOException {
        Pret pret = pretRepository.findById(pretId)
                .orElseThrow(() -> new RuntimeException("Prêt non trouvé avec l'ID: " + pretId));

        byte[] photoData = file.getBytes();
        String contentType = file.getContentType();

        pret.setPhoto(photoData);
        pret.setTypePhoto(contentType);
        pretRepository.save(pret);
    }


}

