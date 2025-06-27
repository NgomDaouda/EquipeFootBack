package com.example.demo.services;

import com.example.demo.dto.TransfertDto;
import com.example.demo.entities.Transfert;
import com.example.demo.repositories.TransfertRepository;
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
public class TransfertServiceImp implements TransfertService{
    @Autowired
    private TransfertRepository transfertRepository;

    @Override
    public Collection<Transfert> getAll() {
        return transfertRepository.findAll();
    }

    @Override
    public Optional<Transfert> getById(Long id) {
        return transfertRepository.findById(id);
    }

    @Override
    public Transfert save(Transfert transfert) {
        return transfertRepository.save(transfert);
    }

    @Override
    public Transfert update(Transfert transfert) {
        return transfertRepository.save(transfert);
    }

    @Override
    public void delete(Long id) {
        transfertRepository.deleteById(id);
    }
    @Override
    public TransfertDto saveTransfert(TransfertDto dto) {
        Transfert transfert = new Transfert();

        transfert.setNom(dto.getNom());
        transfert.setPrenom(dto.getPrenom());
        transfert.setDateTransfert(dto.getDateTransfert());
        transfert.setDateNaissance(dto.getDateNaissance());
        transfert.setStatut(dto.getStatut());
        transfert.setNationalite(dto.getNationalite());
        transfert.setMontant_Transfert(dto.getMontant_Transfert());
        transfert.setTypePhoto(dto.getTypePhoto());
        transfert.setPhoto(dto.getPhoto());
        transfert.setClubOrigine(dto.getClubOrigine());

        // Calcul de l'âge
        if (dto.getDateNaissance() != null) {
            LocalDate naissance = dto.getDateNaissance().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            int age = Period.between(naissance, LocalDate.now()).getYears();
            transfert.setAge(age);
        } else {
            transfert.setAge(null);
        }

        transfert.setDateEnregistrement(LocalDate.now());

        // Sauvegarde
        Transfert saved = transfertRepository.save(transfert);

        // Mise à jour du DTO avec l'ID généré
        dto.setId(saved.getId());
        return dto;
    }

    public void uploadPhoto(Long transfertId, MultipartFile file) throws IOException {
        Transfert transfert = transfertRepository.findById(transfertId)
                .orElseThrow(() -> new RuntimeException("Transfert non trouvé avec l'ID: " + transfertId));

        byte[] photoData = file.getBytes();
        String contentType = file.getContentType();

        transfert.setPhoto(photoData);
        transfert.setTypePhoto(contentType);
        transfertRepository.save(transfert);
    }


}
