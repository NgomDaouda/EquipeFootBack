package com.example.demo.controller;

import com.example.demo.entities.Comptabilite;
import com.example.demo.services.ComptabiliteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/comptabilite")
@CrossOrigin(origins = "*")
public class ComptabiliteController {

    @Autowired
    private ComptabiliteService comptabiliteService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<String> copierPaiements() {
        comptabiliteService.creerComptabilte();
        return ResponseEntity.ok("Données copiées avec succès.");
    }

    @GetMapping()
    public Collection<Comptabilite> getAll() {
        return comptabiliteService.getAll();
    }

}
