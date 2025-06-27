package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class TransfertDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("prenom")
    private String prenom;

    @JsonProperty("dateTransfert")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateTransfert;

    @JsonProperty("dateNaissance")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateNaissance;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("statut")
    private String statut;

    @JsonProperty("clubOrigine")
    private String clubOrigine;

    @JsonProperty("nationalite")
    private String nationalite;

    @JsonProperty("montant_Transfert")
    @Column(precision = 10, scale = 2)
    private BigDecimal montant_Transfert;

    @JsonProperty("dateEnregistrement")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Format ISO recommandé
    private LocalDate dateEnregistrement;

    @Lob
    @JsonProperty("photo")
    private byte[] photo;

    @JsonProperty("typePhoto")
    private String typePhoto;

    // Getters et Setters

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

    public Date getDateTransfert() {
        return dateTransfert;
    }

    public void setDateTransfert(Date dateTransfert) {
        this.dateTransfert = dateTransfert;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getClubOrigine() {
        return clubOrigine;
    }

    public void setClubOrigine(String clubOrigine) {
        this.clubOrigine = clubOrigine;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public BigDecimal getMontant_Transfert() {
        return montant_Transfert;
    }

    public void setMontant_Transfert(BigDecimal montant_Transfert) {
        this.montant_Transfert = montant_Transfert;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getTypePhoto() {
        return typePhoto;
    }

    public void setTypePhoto(String typePhoto) {
        this.typePhoto = typePhoto;
    }

    // Getter
    public LocalDate getDateEnregistrement() {
        return dateEnregistrement;
    }

    // Setter
    public void setDateEnregistrement(LocalDate dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }
}
