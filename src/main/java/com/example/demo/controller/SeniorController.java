package com.example.demo.controller;

import com.example.demo.dto.CadetDto;
import com.example.demo.dto.JoueurDto;
import com.example.demo.dto.SeniorDto;
import com.example.demo.entities.Cadet;
import com.example.demo.entities.Joueur;
import com.example.demo.entities.Senior;
import com.example.demo.services.CadetServiceImp;
import com.example.demo.services.SeniorService;
import com.example.demo.services.SeniorServiceImp;
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
@RequestMapping("/api/senior")
@CrossOrigin(origins = "*")
public class SeniorController {

    @Autowired
    private SeniorService seniorService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<SeniorDto> create(@RequestBody SeniorDto seniorDto) {

        System.out.println("Reçu de Postman : " + seniorDto);

        Senior senior = modelMapper.map(seniorDto, Senior.class);
        Senior savedSenior = ((SeniorServiceImp) seniorService).enregistrerJoueur(seniorDto); // cast temporaire si interface ne contient pas la méthode
        SeniorDto savedJoueurDto = modelMapper.map(savedSenior, SeniorDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedJoueurDto);
    }

    @GetMapping
    public ResponseEntity<Collection<SeniorDto>> getAll() {
        Collection<Senior> seniors = seniorService.getAll();
        List<SeniorDto> seniorDtos = seniors.stream()
                .map(senior -> modelMapper.map(senior, SeniorDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(seniorDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicule(@PathVariable Long id) {
        try {
            seniorService.delete(id);
            return ResponseEntity.ok("joueur supprimé avec succès !");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeniorDto> updateSenior(@PathVariable Long id, @RequestBody SeniorDto seniorDto) {
        SeniorDto updated = seniorService.updateSenior(id, seniorDto);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            seniorService.uploadPhoto(id, file);
            return ResponseEntity.ok("Photo envoyée avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du téléchargement de la photo");
        }
    }



    @GetMapping("/{id}/photo")
    public ResponseEntity<ByteArrayResource> getPhoto(@PathVariable Long id) {
        Optional<Senior> seniorOpt = seniorService.getById(id);
        if (seniorOpt.isPresent() && seniorOpt.get().getPhoto() != null) {
            Senior senior = seniorOpt.get();
            ByteArrayResource resource = new ByteArrayResource(senior.getPhoto());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"photo_" + senior.getId() + "\"")
                    .contentType(MediaType.parseMediaType(senior.getTypePhoto()))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private final Path root = Path.of("uploads/photos");

}
