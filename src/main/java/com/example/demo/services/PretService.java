package com.example.demo.services;

import com.example.demo.dto.JoueurDto;
import com.example.demo.dto.PretDto;
import com.example.demo.entities.Pret;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface PretService {
    Collection<Pret> getAll();
    Optional<Pret> getById(Long id);
    Pret save(Pret pret);
    Pret update(Pret pret);
    void delete(Long id);
    PretDto savePret( PretDto dto);
    void uploadPhoto(Long seniorId, MultipartFile file) throws IOException;

}
