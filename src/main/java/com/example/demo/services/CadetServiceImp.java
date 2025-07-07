package com.example.demo.services;

import com.example.demo.dto.CadetDto;
import com.example.demo.dto.JoueurDto;
import com.example.demo.entities.Cadet;
import com.example.demo.entities.Categorie;
import com.example.demo.entities.Joueur;
import com.example.demo.repositories.CadetRepository;
import com.example.demo.repositories.CategorieRepository;
import com.example.demo.repositories.JoueurRepository;
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
public class CadetServiceImp implements CadetService{

    @Autowired
    private CadetRepository cadetRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private JoueurRepository joueurRepository;
    @Override
    public Collection<Cadet> getAll() {
        return cadetRepository.findAll();
    }
    private final Path root = Path.of("uploads/photos");

    @Override
    public Optional<Cadet> getById(Long id) {
        return cadetRepository.findById(id);
    }

    @Override
    public Cadet save(Cadet cadet) {
        return cadetRepository.save(cadet);
    }

    @Override
    public Cadet update(Cadet cadet) {
        return cadetRepository.save(cadet);
    }

    @Override
    public void delete(Long id) {
        cadetRepository.deleteById(id);
    }

    public Cadet enregistrerJoueur(CadetDto cadetDto) {
        Cadet cadet = new Cadet();

        cadet.setNom(cadetDto.getNom());
        cadet.setPrenom(cadetDto.getPrenom());

        // Calcul de l'âge à partir de la date de naissance
        if (cadetDto.getDateNaissance() != null) {
            LocalDate naissance = cadetDto.getDateNaissance().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            int age = Period.between(naissance, LocalDate.now()).getYears();
            cadet.setAge(age);
        } else {
            cadet.setAge(null); // ou 0 ou une exception selon ta logique métier
        }

        cadet.setDateNaissance(cadetDto.getDateNaissance());
        cadet.setDateArrivee(LocalDate.now());
        cadet.setPoste(cadetDto.getPoste());
        cadet.setPiedFort(cadetDto.getPiedFort());
        cadet.setStatut(cadetDto.getStatut());
        cadet.setNationalite(cadetDto.getNationalite());
        cadet.setPhoto(cadetDto.getPhoto());
        cadet.setTypePhoto(cadetDto.getTypePhoto());
        // Enregistrement du cadet
        Cadet savedCadet = cadetRepository.save(cadet);

        // Création du joueur
        Joueur joueur = new Joueur();
        joueur.setNom(savedCadet.getNom());
        joueur.setPrenom(savedCadet.getPrenom());
        joueur.setAge(savedCadet.getAge());
        joueur.setDateNaissance(savedCadet.getDateNaissance());
        joueur.setDateArrivee(savedCadet.getDateArrivee());
        joueur.setPoste(savedCadet.getPoste());
        joueur.setPiedFort(savedCadet.getPiedFort());
        joueur.setStatut(savedCadet.getStatut());
        joueur.setNationalite(savedCadet.getNationalite());
        joueur.setPhoto(savedCadet.getPhoto());
        joueur.setTypePhoto(savedCadet.getTypePhoto());


        joueurRepository.save(joueur);


        return cadetRepository.save(cadet);
    }
    @Override
    public CadetDto updateCadet(Long id, CadetDto cadetDto) {
        // Recherche du cadet par ID
        Cadet cadet = cadetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cadet introuvable avec l'id: " + id));

        // Mise à jour des champs
        cadet.setNom(cadetDto.getNom());
        cadet.setPrenom(cadetDto.getPrenom());
        cadet.setPoste(cadetDto.getPoste());
        cadet.setPiedFort(cadetDto.getPiedFort());
        cadet.setStatut(cadetDto.getStatut());
        cadet.setNationalite(cadetDto.getNationalite());
        cadet.setDateNaissance(cadetDto.getDateNaissance());
        cadet.setDateArrivee(cadetDto.getDateArrivee());

        // Recalcul de l'âge
        if (cadetDto.getDateNaissance() != null) {
            LocalDate naissance = cadetDto.getDateNaissance().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDate();
            int age = Period.between(naissance, LocalDate.now()).getYears();
            cadet.setAge(age);
        }

        // Sauvegarde du cadet mis à jour
        Cadet updated = cadetRepository.save(cadet);

        // Construction du DTO à retourner
        CadetDto updatedDto = new CadetDto();
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

    /*public void uploadPhoto(Long cadetId, MultipartFile file) throws IOException {
        Cadet cadet = cadetRepository.findById(cadetId)
                .orElseThrow(() -> new RuntimeException("Joueur non trouvé avec l'ID: " + cadetId));

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        cadet.setPhoto(file.getBytes()); // Assure-toi que le champ `photo` existe
        cadet.setTypePhoto(file.getContentType()); // ✅ Ne pas oublier ça

        cadetRepository.save(cadet);
    }*/

    public void uploadPhoto(Long cadetId, MultipartFile file) throws IOException {
        Cadet cadet = cadetRepository.findById(cadetId)
                .orElseThrow(() -> new RuntimeException("Joueur non trouvé avec l'ID: " + cadetId));

        byte[] photoData = file.getBytes();
        String contentType = file.getContentType();

        cadet.setPhoto(photoData);
        cadet.setTypePhoto(contentType);
        cadetRepository.save(cadet);

        // Mise à jour dans joueur aussi (optionnel si logique métier le nécessite)
        Optional<Joueur> joueurOpt = joueurRepository.findByNomAndPrenom(cadet.getNom(), cadet.getPrenom());
        joueurOpt.ifPresent(joueur -> {
            joueur.setPhoto(photoData);
            joueur.setTypePhoto(contentType);
            joueurRepository.save(joueur);
        });
    }

}
