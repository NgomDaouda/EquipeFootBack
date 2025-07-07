package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comptabilite {


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

    @JsonProperty("totalPaiementMensuel")
    private BigDecimal totalPaiementMensuel;

    @ManyToOne
    private Paiement paiement;

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

    public BigDecimal getSalaireMensuel() {
        return salaireMensuel;
    }

    public void setSalaireMensuel(BigDecimal salaireMensuel) {
        this.salaireMensuel = salaireMensuel;
    }

    public BigDecimal getTotalPaiementMensuel() {
        return totalPaiementMensuel;
    }

    public void setTotalPaiementMensuel(BigDecimal totalPaiementMensuel) {
        this.totalPaiementMensuel = totalPaiementMensuel;
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
