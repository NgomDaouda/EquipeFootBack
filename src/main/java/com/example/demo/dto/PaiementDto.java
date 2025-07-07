package com.example.demo.dto;

import com.example.demo.entities.Salaire;
import com.example.demo.enume.StatutPaiement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaiementDto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("prenom")
    private String prenom;

    @JsonProperty("datePaiement")
    @Temporal(TemporalType.DATE)
    private Date datePaiement;

    @JsonProperty("salaireMensuel")
    private BigDecimal salaireMensuel;

    @JsonProperty("fonction")
    private String fonction;

    @JsonProperty("statut")
    private StatutPaiement statut;

    @ManyToOne
    private Salaire salaire;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public StatutPaiement getStatut() {
        return statut;
    }

    public void setStatut(StatutPaiement statut) {
        this.statut = statut;
    }

    public BigDecimal getSalaireMensuel() {
        return salaireMensuel;
    }

    public void setSalaireMensuel(BigDecimal salaireMensuel) {
        this.salaireMensuel = salaireMensuel;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }
}
