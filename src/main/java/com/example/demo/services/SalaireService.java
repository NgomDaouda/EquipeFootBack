package com.example.demo.services;

import com.example.demo.dto.SalaireDto;
import com.example.demo.entities.Salaire;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface SalaireService {

    Collection<Salaire> getAll();
    Optional<Salaire> getById(Long id);
    Salaire save(Salaire salaire);
    Salaire update(Salaire salaire);
    void deleteById(Long id);
    SalaireDto saveSalaire(SalaireDto dto);
    void uploadPhoto(Long salaireId, MultipartFile file) throws IOException;

}
