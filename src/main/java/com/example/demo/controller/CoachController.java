package com.example.demo.controller;

import com.example.demo.dto.CoachDto;
import com.example.demo.dto.JoueurDto;
import com.example.demo.entities.Categorie;
import com.example.demo.entities.Coach;
import com.example.demo.entities.Joueur;
import com.example.demo.services.CategorieService;
import com.example.demo.services.CoachService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coach")
public class CoachController {

    @Autowired
    private   CoachService coachService;
    @Autowired
    private CategorieService categorieService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<CoachDto> create(@RequestBody CoachDto coachDto) {

        System.out.println("Reçu de Postman : " + coachDto);

        Categorie categorie = categorieService.getById(coachDto.getCategorieId())
                .orElseThrow(() -> new EntityNotFoundException("Categorie not found with id: " + coachDto.getCategorieId()));

        Coach coach = modelMapper.map(coachDto, Coach.class);
        coach.setCategorie(categorie);
        Coach savedCoach = coachService.save(coach);
        CoachDto savedcoachDto = modelMapper.map(savedCoach, CoachDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedcoachDto);
    }

    @GetMapping
    public ResponseEntity<Collection<CoachDto>> getAll() {
        Collection<Coach> coachs = coachService.getAll();
        List<CoachDto> coachDtos = coachs.stream()
                .map(coach -> modelMapper.map(coach, CoachDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(coachDtos);
    }
}
