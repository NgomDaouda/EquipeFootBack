package com.example.demo.controller;

import com.example.demo.dto.SalaireDto;
import com.example.demo.entities.Salaire;
import com.example.demo.services.SalaireService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/salaire")
@CrossOrigin(origins = "*")
public class SalaireController {

    @Autowired
    private SalaireService salaireService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Créer un salaire
     */
    @PostMapping
    public ResponseEntity<SalaireDto> create(@RequestBody SalaireDto salaireDto) {
        SalaireDto saved = salaireService.saveSalaire(salaireDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Récupérer tous les salaires
     */
    @GetMapping
    public ResponseEntity<List<SalaireDto>> getAll() {
        Collection<Salaire> salaires = salaireService.getAll();
        List<SalaireDto> dtos = salaires.stream()
                .map(salaire -> modelMapper.map(salaire, SalaireDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Récupérer un salaire par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalaireDto> getById(@PathVariable Long id) {
        Optional<Salaire> salaireOpt = salaireService.getById(id);
        return salaireOpt.map(salaire -> {
            SalaireDto dto = modelMapper.map(salaire, SalaireDto.class);
            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Modifier un salaire
     */
    @PutMapping("/{id}")
    public ResponseEntity<SalaireDto> update(@PathVariable Long id, @RequestBody SalaireDto salaireDto) {
        Salaire updated = modelMapper.map(salaireDto, Salaire.class);
        updated.setId(id);
        Salaire saved = salaireService.update(updated);
        SalaireDto savedDto = modelMapper.map(saved, SalaireDto.class);
        return ResponseEntity.ok(savedDto);
    }

    /**
     * Supprimer un salaire
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            salaireService.deleteById(id);
            return ResponseEntity.ok("Salaire supprimé avec succès");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Upload photo
     */
    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            salaireService.uploadPhoto(id, file);
            return ResponseEntity.ok("Photo envoyée avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'upload de la photo");
        }
    }

    /**
     * Récupérer la photo
     */
    @GetMapping("/{id}/photo")
    public ResponseEntity<ByteArrayResource> getPhoto(@PathVariable Long id) {
        Optional<Salaire> salaireOpt = salaireService.getById(id);
        if (salaireOpt.isPresent() && salaireOpt.get().getPhoto() != null) {
            Salaire salaire = salaireOpt.get();
            ByteArrayResource resource = new ByteArrayResource(salaire.getPhoto());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"photo_" + salaire.getId() + "\"")
                    .contentType(MediaType.parseMediaType(salaire.getTypePhoto()))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
