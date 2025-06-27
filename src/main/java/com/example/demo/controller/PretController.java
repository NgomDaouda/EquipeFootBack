package com.example.demo.controller;

import com.example.demo.dto.PretDto;
import com.example.demo.entities.Pret;
import com.example.demo.services.PretService;
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
@RequestMapping("/api/pret")
@CrossOrigin(origins = "*")
public class PretController {

    @Autowired
    private PretService pretService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Création d'un prêt
     */
    @PostMapping
    public ResponseEntity<PretDto> create(@RequestBody PretDto pretDto) {
        System.out.println("Reçu de Postman : " + pretDto);
        PretDto savedDto = pretService.savePret(pretDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    /**
     * Liste de tous les prêts
     */
    @GetMapping
    public ResponseEntity<Collection<PretDto>> getAll() {
        Collection<Pret> prets = pretService.getAll();
        List<PretDto> pretDtos = prets.stream()
                .map(pret -> modelMapper.map(pret, PretDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(pretDtos);
    }

    /**
     * Suppression d’un prêt
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            pretService.delete(id);
            return ResponseEntity.ok("Prêt supprimé avec succès !");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Mise à jour d’un prêt
     */
    @PutMapping("/{id}")
    public ResponseEntity<PretDto> update(@PathVariable Long id, @RequestBody PretDto pretDto) {
        Pret pret = modelMapper.map(pretDto, Pret.class);
        pret.setId(id);
        Pret updated = pretService.update(pret);
        PretDto updatedDto = modelMapper.map(updated, PretDto.class);
        return ResponseEntity.ok(updatedDto);
    }

    /**
     * Récupération d’un prêt par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PretDto> getById(@PathVariable Long id) {
        Optional<Pret> pretOpt = pretService.getById(id);
        if (pretOpt.isPresent()) {
            PretDto dto = modelMapper.map(pretOpt.get(), PretDto.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            pretService.uploadPhoto(id, file);
            return ResponseEntity.ok("Photo envoyée avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors du téléchargement de la photo");
        }
    }

    /**
     * Récupération de la photo
     */
    @GetMapping("/{id}/photo")
    public ResponseEntity<ByteArrayResource> getPhoto(@PathVariable Long id) {
        Optional<Pret> pretOpt = pretService.getById(id);
        if (pretOpt.isPresent() && pretOpt.get().getPhoto() != null) {
            Pret pret = pretOpt.get();
            ByteArrayResource resource = new ByteArrayResource(pret.getPhoto());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"photo_" + pret.getId() + "\"")
                    .contentType(MediaType.parseMediaType(pret.getTypePhoto()))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
