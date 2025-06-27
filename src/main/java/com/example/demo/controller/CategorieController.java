package com.example.demo.controller;

import com.example.demo.entities.Categorie;
import com.example.demo.services.CategorieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/categorie")
@CrossOrigin("*")
public class CategorieController {
    private final CategorieService _categorieService;

    public CategorieController(CategorieService categorieService) {
        _categorieService = categorieService;
    }

    @PostMapping
    public ResponseEntity<Categorie> save(@RequestBody Categorie categorie) {
        Categorie savedCategorie = _categorieService.save(categorie);
        return new ResponseEntity<>(savedCategorie, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Categorie>> getAll() {
        Collection<Categorie> categorie = _categorieService.getAll();
        return new ResponseEntity<>(categorie, HttpStatus.OK);
    }
}
