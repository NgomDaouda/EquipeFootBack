package com.example.demo.services;

import com.example.demo.dto.CadetDto;
import com.example.demo.dto.JoueurDto;
import com.example.demo.dto.SeniorDto;
import com.example.demo.entities.Cadet;
import com.example.demo.entities.Joueur;
import com.example.demo.entities.Senior;
import com.example.demo.repositories.JoueurRepository;
import com.example.demo.repositories.SeniorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SeniorServiceImp implements SeniorService{
    @Autowired
    private SeniorRepository seniorRepository;

    @Autowired
    private JoueurRepository joueurRepository;

    private final Path root = Path.of("uploads/photos");

    @Override
    public Collection<Senior> getAll() {
        return seniorRepository.findAll();
    }

    @Override
    public Optional<Senior> getById(Long id) {
        return seniorRepository.findById(id);
    }

    @Override
    public Senior save(Senior senior) {
        return seniorRepository.save(senior);
    }

    @Override
    public Senior update(Senior senior) {
        return seniorRepository.save(senior);
    }

    @Override
    public void delete(Long id) {
        seniorRepository.deleteById(id);
    }



    public Senior enregistrerJoueur(SeniorDto seniorDto) {
        Senior senior = new Senior();

        senior.setNom(seniorDto.getNom());
        senior.setPrenom(seniorDto.getPrenom());

        // Calcul de l'âge à partir de la date de naissance
        if (seniorDto.getDateNaissance() != null) {
            LocalDate naissance = seniorDto.getDateNaissance().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            int age = Period.between(naissance, LocalDate.now()).getYears();
            senior.setAge(age);
        } else {
            senior.setAge(null); // ou 0 ou une exception selon ta logique métier
        }

        senior.setDateNaissance(seniorDto.getDateNaissance());
        senior.setDateArrivee(LocalDate.now());
        senior.setPoste(seniorDto.getPoste());
        senior.setPiedFort(seniorDto.getPiedFort());
        senior.setStatut(seniorDto.getStatut());
        senior.setNationalite(seniorDto.getNationalite());
        senior.setPhoto(seniorDto.getPhoto());
        senior.setTypePhoto(seniorDto.getTypePhoto());

        System.out.println("Nationalité reçue : " + seniorDto.getNationalite());

        // Sauvegarde du senior
        Senior savedSenior = seniorRepository.save(senior);

        // Création et enregistrement du joueur
        Joueur joueur = new Joueur();
        joueur.setNom(savedSenior.getNom());
        joueur.setPrenom(savedSenior.getPrenom());
        joueur.setAge(savedSenior.getAge());
        joueur.setDateNaissance(savedSenior.getDateNaissance());
        joueur.setPoste(savedSenior.getPoste());
        joueur.setPiedFort(savedSenior.getPiedFort());
        joueur.setNationalite(savedSenior.getNationalite());
        joueur.setStatut(savedSenior.getStatut());
        joueur.setDateArrivee(savedSenior.getDateArrivee());
        joueur.setPhoto(seniorDto.getPhoto());
        joueur.setTypePhoto(seniorDto.getTypePhoto());


        joueurRepository.save(joueur);

        return seniorRepository.save(senior);
    }

    @Override
    public SeniorDto updateSenior(Long id, SeniorDto seniorDto) {
        Senior senior = seniorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Joueur introuvable avec l'id: " + id));

        senior.setNom(seniorDto.getNom());
        senior.setPrenom(seniorDto.getPrenom());
        senior.setPoste(seniorDto.getPoste());
        senior.setPiedFort(seniorDto.getPiedFort());
        senior.setStatut(seniorDto.getStatut());
        senior.setNationalite(seniorDto.getNationalite());
        senior.setDateNaissance(seniorDto.getDateNaissance());
        senior.setDateArrivee(seniorDto.getDateArrivee());

        // Calcul de l'âge
        if (seniorDto.getDateNaissance() != null) {
            LocalDate naissance = seniorDto.getDateNaissance().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            int age = Period.between(naissance, LocalDate.now()).getYears();
            senior.setAge(age);
        }

        Senior updated = seniorRepository.save(senior);

        // Construction manuelle du DTO retourné
        SeniorDto updatedDto = new SeniorDto();
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


    public void uploadPhoto(Long seniorId, MultipartFile file) throws IOException {
        Senior senior = seniorRepository.findById(seniorId)
                .orElseThrow(() -> new RuntimeException("Joueur non trouvé avec l'ID: " + seniorId));

        byte[] photoData = file.getBytes();
        String contentType = file.getContentType();

        // Enregistrement de la photo dans l'entité Senior
        senior.setPhoto(photoData);
        senior.setTypePhoto(contentType);
        seniorRepository.save(senior);

        // Mise à jour dans l'entité Joueur également
        Optional<Joueur> joueurOpt = joueurRepository.findByNomAndPrenom(senior.getNom(), senior.getPrenom());
        joueurOpt.ifPresent(joueur -> {
            joueur.setPhoto(photoData);
            joueur.setTypePhoto(contentType);
            joueurRepository.save(joueur);
        });
    }


    public Resource loadPhoto(Long id) throws IOException {
        Senior senior = seniorRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("Joueur non trouvé"));

        if (senior.getTypePhoto() == null) {
            throw new FileNotFoundException("Aucune photo enregistrée pour ce joueur");
        }

        Path file = root.resolve(senior.getTypePhoto());
        if (!Files.exists(file)) {
            throw new FileNotFoundException("Fichier introuvable : " + senior.getTypePhoto());
        }

        return new UrlResource(file.toUri());
    }
}
