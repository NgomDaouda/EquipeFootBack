package com.example.demo.controller;

import com.example.demo.dto.JoueurDto;
import com.example.demo.dto.VenteDto;
import com.example.demo.entities.Joueur;
import com.example.demo.entities.Vente;
import com.example.demo.services.JoueurServiceImp;
import com.example.demo.services.VenteService;
import com.example.demo.services.VenteServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vente")
@CrossOrigin(origins = "*")
public class VenteController {

    @Autowired
    private VenteService venteService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<VenteDto> create(@RequestBody VenteDto venteDto) {

        System.out.println("Reçu de Postman : " + venteDto);


        Vente vente = modelMapper.map(venteDto, Vente.class);
        Vente savedVente = ((VenteServiceImp) venteService).enregistrerVente(venteDto); // cast temporaire si interface ne contient pas la méthode
        VenteDto savedVenteDto = modelMapper.map(savedVente, VenteDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedVenteDto);
    }

//    @PostMapping
//    public ResponseEntity<VenteDto> saveVente(@RequestBody VenteDto dto) {
//        try {
//            Vente vente = modelMapper.map(dto, Vente.class);
//            Vente saved = venteService.save(vente);
//            VenteDto response = modelMapper.map(saved, VenteDto.class);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace(); // 👈 à voir dans la console
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping
    public ResponseEntity<Collection<VenteDto>> getAll() {
        Collection<Vente> ventes = venteService.getAll();
        List<VenteDto> venteDtos = ventes.stream()
                .map(vente -> modelMapper.map(vente, VenteDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(venteDtos);
    }

}
