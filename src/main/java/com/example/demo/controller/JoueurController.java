package com.example.demo.controller;

import com.example.demo.dto.JoueurDto;
import com.example.demo.entities.Categorie;
import com.example.demo.entities.Joueur;
import com.example.demo.services.CategorieService;
import com.example.demo.services.JoueurService;
import com.example.demo.services.JoueurServiceImp;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/joueur")
@CrossOrigin(origins = "*")
public class JoueurController {

    @Autowired
    private JoueurService joueurService;

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<JoueurDto> create(@RequestBody JoueurDto joueurDto) {

        System.out.println("Reçu de Postman : " + joueurDto);


        Joueur joueur = modelMapper.map(joueurDto, Joueur.class);
        Joueur savedJoueur = ((JoueurServiceImp) joueurService).enregistrerJoueur(joueurDto); // cast temporaire si interface ne contient pas la méthode
        JoueurDto savedJoueurDto = modelMapper.map(savedJoueur, JoueurDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedJoueurDto);
    }

    @GetMapping
    public ResponseEntity<Collection<JoueurDto>> getAll() {
        Collection<Joueur> joueurs = joueurService.getAll();
        List<JoueurDto> joueurDtos = joueurs.stream()
                .map(joueur -> modelMapper.map(joueur, JoueurDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(joueurDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicule(@PathVariable Long id) {
        try {
            joueurService.delete(id);
            return ResponseEntity.ok("joueur supprimé avec succès !");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Joueur> getById(@PathVariable Long id) {
        Optional<Joueur> joueur = joueurService.getById(id);
        return joueur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<JoueurDto> updateJoueur(@PathVariable Long id, @RequestBody JoueurDto joueurDto) {
        JoueurDto updated = joueurService.updateJoueur(id, joueurDto);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            joueurService.uploadPhoto(id, file);
            return ResponseEntity.ok("Photo envoyée avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du téléchargement de la photo");
        }
    }



    @GetMapping("/{id}/photo")
    public ResponseEntity<ByteArrayResource> getPhoto(@PathVariable Long id) {
        Optional<Joueur> joueurOpt = joueurService.getById(id);
        if (joueurOpt.isPresent() && joueurOpt.get().getPhoto() != null) {
            Joueur joueur = joueurOpt.get();
            ByteArrayResource resource = new ByteArrayResource(joueur.getPhoto());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"photo_" + joueur.getId() + "\"")
                    .contentType(MediaType.parseMediaType(joueur.getTypePhoto()))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
