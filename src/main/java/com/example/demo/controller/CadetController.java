package com.example.demo.controller;

import com.example.demo.dto.CadetDto;
import com.example.demo.dto.JoueurDto;
import com.example.demo.dto.SeniorDto;
import com.example.demo.entities.Cadet;
import com.example.demo.entities.Categorie;
import com.example.demo.entities.Joueur;
import com.example.demo.entities.Senior;
import com.example.demo.services.*;
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
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cadet")
@CrossOrigin(origins = "*")
public class CadetController {

    @Autowired
    private CadetService cadetService;

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CadetDto> create(@RequestBody CadetDto cadetDto) {

        System.out.println("Reçu de Postman : " + cadetDto);

//        Categorie categorie = categorieService.getById(cadetDto.getCategorieId())
//                .orElseThrow(() -> new EntityNotFoundException("Categorie not found with id: " + cadetDto.getCategorieId()));

        Cadet cadet = modelMapper.map(cadetDto, Cadet.class);
        //cadet.setCategorie(categorie);
        Cadet savedCadet = ((CadetServiceImp) cadetService).enregistrerJoueur(cadetDto); // cast temporaire si interface ne contient pas la méthode
        CadetDto savedJoueurDto = modelMapper.map(savedCadet, CadetDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedJoueurDto);
    }

    @GetMapping
    public ResponseEntity<Collection<CadetDto>> getAll() {
        Collection<Cadet> cadets = cadetService.getAll();
        List<CadetDto> cadetDtos = cadets.stream()
                .map(cadet -> modelMapper.map(cadet, CadetDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(cadetDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CadetDto> up(@PathVariable Long id, @RequestBody CadetDto cadetDto) {
        CadetDto updated = cadetService.updateCadet(id, cadetDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicule(@PathVariable Long id) {
        try {
            cadetService.delete(id);
            return ResponseEntity.ok("joueur supprimé avec succès !");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            cadetService.uploadPhoto(id, file);
            return ResponseEntity.ok("Photo envoyée avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du téléchargement de la photo");
        }
    }



    @GetMapping("/{id}/photo")
    public ResponseEntity<ByteArrayResource> getPhoto(@PathVariable Long id) {
        Optional<Cadet> cadetOpt = cadetService.getById(id);
        if (cadetOpt.isPresent() && cadetOpt.get().getPhoto() != null) {
            Cadet cadet = cadetOpt.get();
            ByteArrayResource resource = new ByteArrayResource(cadet.getPhoto());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"photo_" + cadet.getId() + "\"")
                    .contentType(MediaType.parseMediaType(cadet.getTypePhoto()))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private final Path root = Path.of("uploads/photos");

}
