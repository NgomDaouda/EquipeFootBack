package com.example.demo.services;

import com.example.demo.dto.SalaireDto;
import com.example.demo.entities.Salaire;
import com.example.demo.repositories.SalaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SalaireServiceImp implements SalaireService{

    @Autowired
    private SalaireRepository salaireRepository;

    @Override
    public Collection<Salaire> getAll() {
        return salaireRepository.findAll();
    }

    @Override
    public Optional<Salaire> getById(Long id) {
        return salaireRepository.findById(id);
    }

    @Override
    public Salaire save(Salaire salaire) {
        return salaireRepository.save(salaire);
    }

    @Override
    public Salaire update(Salaire salaire) {
        return salaireRepository.save(salaire);
    }

    @Override
    public void deleteById(Long id) {
        salaireRepository.deleteById(id);
    }

    @Override
    public SalaireDto saveSalaire(SalaireDto dto) {
        Salaire salaire = new Salaire();

        salaire.setNom(dto.getNom());
        salaire.setPrenom(dto.getPrenom());
        salaire.setDateNaissance(dto.getDateNaissance());
        salaire.setFonction(dto.getFonction());
        salaire.setNationalite(dto.getNationalite());
        salaire.setSalaireMensuel(dto.getSalaireMensuel());

        // ✅ Calcul systématique du salaire annuel = salaireMensuel * 12
        if (dto.getSalaireMensuel() != null) {
            BigDecimal salaireAnnuel = dto.getSalaireMensuel().multiply(BigDecimal.valueOf(12));
            salaire.setSalaireAnnuel(salaireAnnuel);
        } else {
            salaire.setSalaireAnnuel(BigDecimal.ZERO); // ou null, selon ton besoin
        }

        salaire.setPhoto(dto.getPhoto());
        salaire.setTypePhoto(dto.getTypePhoto());

        // Enregistrement en base
        Salaire saved = salaireRepository.save(salaire);

        // Mise à jour du DTO retourné
        dto.setId(saved.getId());
        dto.setSalaireAnnuel(saved.getSalaireAnnuel()); // on retourne le bon calcul

        return dto;    }

    @Override
    public void uploadPhoto(Long salaireId, MultipartFile file) throws IOException {
        Salaire salaire = salaireRepository.findById(salaireId)
                .orElseThrow(() -> new RuntimeException("Salaire non trouvé avec l'ID: " + salaireId));

        byte[] photoData = file.getBytes();
        String contentType = file.getContentType();

        salaire.setPhoto(photoData);
        salaire.setTypePhoto(contentType);
        salaireRepository.save(salaire);
    }


}
