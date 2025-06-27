package com.example.demo.controller;

import com.example.demo.entities.Paiement;
import com.example.demo.services.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paiement")
@CrossOrigin(origins = "*")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    // GET /paiements
    @GetMapping
    public Collection<Paiement> getAllPaiements() {
        return paiementService.getAll();
    }

    // GET /paiements/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable Long id) {
        Optional<Paiement> paiement = paiementService.getById(id);
        return paiement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /paiements
    @PostMapping
    public ResponseEntity<Paiement> createPaiement(@RequestBody Paiement paiement) {
        Paiement savedPaiement = paiementService.save(paiement);
        return ResponseEntity.ok(savedPaiement);
    }
}
