package com.example.demo.controller;

import com.example.demo.entities.Paiement;
import com.example.demo.enume.StatutPaiement;
import com.example.demo.services.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PutMapping("/{id}/valider")
    public ResponseEntity<String> validerPaiement(@PathVariable Long id) {
        boolean result = paiementService.validerPaiement(id);

        if (result) {
            return ResponseEntity.ok("✅ Paiement validé.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ Paiement introuvable ou déjà validé.");
        }
    }

    @PutMapping("/{id}/payer")
    public ResponseEntity<String> payerSalaire(@PathVariable Long id) {
        boolean result = paiementService.payerSalaire(id);

        if (result) {
            return ResponseEntity.ok("✅ Paiement validé.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("❌ Paiement introuvable ou déjà validé.");
        }
    }

    @PutMapping("/{id}/refuser")
    public ResponseEntity<String> refuserPaiement(
            @PathVariable Long id,
            @RequestBody String justification) {

        Optional<Paiement> optional = paiementService.getById(id);
        if (optional.isPresent()) {
            Paiement paiement = optional.get();
            if (paiement.getStatut() == StatutPaiement.EN_ATTENTE) {
                paiement.setStatut(StatutPaiement.REFUSE);
                // Si vous voulez stocker la justification, ajoutez un champ `justificationRefus`
                paiementService.save(paiement);
                return ResponseEntity.ok("Paiement refusé");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paiement non trouvé ou déjà traité");
    }


    // PUT /paiements/{id}
   /* @PutMapping("/{id}")
    public ResponseEntity<Paiement> updatePaiement(@PathVariable Long id, @RequestBody Paiement paiementDetails) {
        Optional<Paiement> paiementOpt = paiementService.getPaiementById(id);
        if (paiementOpt.isPresent()) {
            Paiement paiement = paiementOpt.get();
            paiement.setNom(paiementDetails.getNom());
            paiement.setPrenom(paiementDetails.getPrenom());
            paiement.setMontant(paiementDetails.getMontant());
            paiement.setDatePaiement(paiementDetails.getDatePaiement());
            paiement.setFonction(paiementDetails.getFonction());
            paiement.setSalaire(paiementDetails.getSalaire());

            Paiement updated = paiementService.savePaiement(paiement);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /paiements/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        if (paiementService.getPaiementById(id).isPresent()) {
            paiementService.deletePaiement(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
}
