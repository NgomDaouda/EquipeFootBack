package com.example.demo.controller;

import com.example.demo.dto.TransfertDto;
import com.example.demo.entities.Transfert;
import com.example.demo.services.TransfertService;
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
@RequestMapping("/api/transfert")
@CrossOrigin(origins = "*")
public class TransfertController {

    @Autowired
    private TransfertService transfertService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<TransfertDto> create(@RequestBody TransfertDto transfertDto) {
        System.out.println("Reçu de Postman : " + transfertDto);

        TransfertDto savedDto = transfertService.saveTransfert(transfertDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    @GetMapping
    public ResponseEntity<Collection<TransfertDto>> getAll() {
        Collection<Transfert> transferts = transfertService.getAll();
        List<TransfertDto> transfertDtos = transferts.stream()
                .map(transfert -> modelMapper.map(transfert, TransfertDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(transfertDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            transfertService.delete(id);
            return ResponseEntity.ok("Transfert supprimé avec succès !");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransfertDto> update(@PathVariable Long id, @RequestBody TransfertDto transfertDto) {
        // Tu peux implémenter une méthode `updateTransfert(id, dto)` dans le service si besoin
        Transfert transfert = modelMapper.map(transfertDto, Transfert.class);
        transfert.setId(id);
        Transfert updated = transfertService.update(transfert);
        TransfertDto updatedDto = modelMapper.map(updated, TransfertDto.class);
        return ResponseEntity.ok(updatedDto);
    }

    @PostMapping("/{id}/photo")
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            transfertService.uploadPhoto(id, file);
            return ResponseEntity.ok("Photo envoyée avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du téléchargement de la photo");
        }
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<ByteArrayResource> getPhoto(@PathVariable Long id) {
        Optional<Transfert> transfertOpt = transfertService.getById(id);
        if (transfertOpt.isPresent() && transfertOpt.get().getPhoto() != null) {
            Transfert transfert = transfertOpt.get();
            ByteArrayResource resource = new ByteArrayResource(transfert.getPhoto());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"photo_" + transfert.getId() + "\"")
                    .contentType(MediaType.parseMediaType(transfert.getTypePhoto()))
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
