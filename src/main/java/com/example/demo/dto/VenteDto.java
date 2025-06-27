package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VenteDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("prenom")
    private String prenom;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("dateVente")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Format ISO recommandé
    private LocalDate dateVente;

    @JsonProperty("poste")
    private String poste;

    @JsonProperty("piedFort")
    private String piedFort;

    @JsonProperty("dateNaissance")
    private Date dateNaissance;

    @JsonProperty("statut")
    private String statut;

    @JsonProperty("nationalite")
    private String nationalite;

    @JsonProperty("prix_Vent")
    @Column(precision = 10, scale = 2)
    private BigDecimal prixVent;
    public BigDecimal getPrixVent() { return prixVent;}
    public void setPrixVent(BigDecimal prixVent) {
        this.prixVent = prixVent;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }

    public LocalDate getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente = dateVente;
    }

    public String getPiedFort() { return piedFort; }
    public void setPiedFort(String piedFort) { this.piedFort = piedFort; }

    public String getStatut() {
        return statut;
    }
    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Date getDateNaissance() { return dateNaissance;}
    public void setDateNaissance(Date dateNaissance) {this.dateNaissance=dateNaissance;}

    public String getNationalite() {
        return nationalite;
    }
    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }
}
