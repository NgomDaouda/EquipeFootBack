package com.example.demo.services;

import com.example.demo.dto.JoueurDto;
import com.example.demo.entities.Joueur;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public interface JoueurService {
    Collection<Joueur>getAll();
    Optional<Joueur>getById(Long id);
    Joueur save(Joueur joueur);
    Joueur update(Long id,Joueur joueur);
    void delete(Long id);
    JoueurDto updateJoueur(Long id, JoueurDto joueurDto);
    void uploadPhoto(Long joueurId, MultipartFile file) throws IOException;

}
