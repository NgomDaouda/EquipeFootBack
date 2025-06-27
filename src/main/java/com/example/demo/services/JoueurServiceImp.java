package com.example.demo.services;

import com.example.demo.dto.JoueurDto;
import com.example.demo.entities.Cadet;
import com.example.demo.entities.Categorie;
import com.example.demo.entities.Joueur;
import com.example.demo.entities.Senior;
import com.example.demo.repositories.CadetRepository;
import com.example.demo.repositories.CategorieRepository;
import com.example.demo.repositories.JoueurRepository;
import com.example.demo.repositories.SeniorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JoueurServiceImp implements JoueurService{

    @Autowired
    private JoueurRepository joueurRepository;
    @Autowired
    private CadetRepository cadetRepository;
    @Autowired
    private SeniorRepository seniorRepository;
    private final Path root = Paths.get("uploads/joueur");

    //
//    @Autowired
//    private ModelMapper modelMapper;
    @Override
    public Collection<Joueur> getAll() {
        return joueurRepository.findAll();
    }

    @Override
    public Optional<Joueur> getById(Long id) {
        return joueurRepository.findById(id);
    }

    @Override
    public Joueur save(Joueur joueur) {
        return joueurRepository.save(joueur);
    }

    @Override
    public Joueur update(Long id, Joueur joueur) {
        return joueurRepository.save(joueur);
    }

    @Override
    public void delete(Long id) {
        joueurRepository.deleteById(id);

    }


    public Joueur enregistrerJoueur(JoueurDto joueurDto) {
        Joueur joueur = new Joueur();

        joueur.setNom(joueurDto.getNom());
        joueur.setPrenom(joueurDto.getPrenom());

        // Calcul de l'âge
        if (joueurDto.getDateNaissance() != null) {
            LocalDate naissance = joueurDto.getDateNaissance().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            int age = Period.between(naissance, LocalDate.now()).getYears();
            joueur.setAge(age);
        } else {
            joueur.setAge(null);
        }

        joueur.setDateNaissance(joueurDto.getDateNaissance());
        joueur.setDateArrivee(LocalDate.now());
        joueur.setPoste(joueurDto.getPoste());
        joueur.setPiedFort(joueurDto.getPiedFort());
        joueur.setStatut(joueurDto.getStatut());

        // Sauvegarde du joueur dans la base
        Joueur savedJoueur = joueurRepository.save(joueur);

        if ("CADET".equalsIgnoreCase(joueurDto.getStatut())) {
            Cadet cadet = new Cadet();
            cadet.setPrenom(savedJoueur.getPrenom());
            cadet.setNom(savedJoueur.getNom());
            if (joueurDto.getDateNaissance() != null) {
                LocalDate naissance = joueurDto.getDateNaissance().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                int age = Period.between(naissance, LocalDate.now()).getYears();
                joueur.setAge(age);
            } else {
                joueur.setAge(null);
            }
            cadet.setAge(savedJoueur.getAge());
            cadet.setDateNaissance(savedJoueur.getDateNaissance());
            cadet.setDateArrivee(LocalDate.now());
            cadet.setPoste(savedJoueur.getPoste());
            cadet.setPiedFort(savedJoueur.getPiedFort());
            cadet.setStatut(savedJoueur.getStatut());
            //achat.setOperation(savedOperation); // Si tu as un lien vers l’opération

            cadetRepository.save(cadet);
        }

        if ("SENIOR".equalsIgnoreCase(joueurDto.getStatut())) {
            Senior senior = new Senior();
            senior.setPrenom(savedJoueur.getPrenom());
            senior.setNom(savedJoueur.getNom());
            if (joueurDto.getDateNaissance() != null) {
                LocalDate naissance = joueurDto.getDateNaissance().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                int age = Period.between(naissance, LocalDate.now()).getYears();
                joueur.setAge(age);
            } else {
                joueur.setAge(null);
            }
            senior.setAge(savedJoueur.getAge());
            senior.setDateNaissance(savedJoueur.getDateNaissance());
            senior.setDateArrivee(LocalDate.now());
            senior.setPoste(savedJoueur.getPoste());
            senior.setPiedFort(savedJoueur.getPiedFort());
            senior.setStatut(savedJoueur.getStatut());
            //achat.setOperation(savedOperation); // Si tu as un lien vers l’opération

            seniorRepository.save(senior);
        }

        return savedJoueur;
    }
    @Override
    public JoueurDto updateJoueur(Long id, JoueurDto joueurDto) {
        Joueur joueur = joueurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Joueur introuvable avec l'id: " + id));

        joueur.setNom(joueurDto.getNom());
        joueur.setPrenom(joueurDto.getPrenom());
        joueur.setPoste(joueurDto.getPoste());
        joueur.setPiedFort(joueurDto.getPiedFort());
        joueur.setStatut(joueurDto.getStatut());
        joueur.setNationalite(joueurDto.getNationalite());
        joueur.setDateNaissance(joueurDto.getDateNaissance());
        joueur.setDateArrivee(joueurDto.getDateArrivee());

        // Calcul de l'âge
        if (joueurDto.getDateNaissance() != null) {
            LocalDate naissance = joueurDto.getDateNaissance().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            int age = Period.between(naissance, LocalDate.now()).getYears();
            joueur.setAge(age);
        }

        Joueur updated = joueurRepository.save(joueur);

        // Construction manuelle du DTO retourné
        JoueurDto updatedDto = new JoueurDto();
        updatedDto.setId(updated.getId());
        updatedDto.setNom(updated.getNom());
        updatedDto.setPrenom(updated.getPrenom());
        updatedDto.setAge(updated.getAge());
        updatedDto.setPoste(updated.getPoste());
        updatedDto.setPiedFort(updated.getPiedFort());
        updatedDto.setStatut(updated.getStatut());
        updatedDto.setNationalite(updated.getNationalite());
        updatedDto.setDateNaissance(updated.getDateNaissance());
        updatedDto.setDateArrivee(updated.getDateArrivee());

        return updatedDto;
    }

    public Resource loadPhoto(Long id) throws IOException {
        Joueur joueur = joueurRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("Joueur non trouvé"));

        if (joueur.getTypePhoto() == null) {
            throw new FileNotFoundException("Aucune photo enregistrée pour ce joueur");
        }

        Path file = root.resolve(joueur.getTypePhoto());
        if (!Files.exists(file)) {
            throw new FileNotFoundException("Fichier introuvable : " + joueur.getTypePhoto());
        }

        return new UrlResource(file.toUri());
    }

    public void uploadPhoto(Long joueurId, MultipartFile file) throws IOException {
        Joueur joueur = joueurRepository.findById(joueurId)
                .orElseThrow(() -> new RuntimeException("Joueur non trouvé avec l'ID: " + joueurId));

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        joueur.setPhoto(file.getBytes()); // Assure-toi que le champ `photo` existe
        joueur.setTypePhoto(file.getContentType()); // ✅ Ne pas oublier ça

        joueurRepository.save(joueur);
    }

}
