package com.parking.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parkings")
public class Parking implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nom", nullable = false)
    private String nom;
    
    @Column(name = "adresse", nullable = false)
    private String adresse;
    
    @Column(name = "capacite", nullable = false)
    private Integer capacite;
    
    @Column(name = "latitude")
    private Double latitude;
    
    @Column(name = "longitude")
    private Double longitude;
    
    @Column(name = "tarif_horaire")
    private Double tarifHoraire;
    
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Place> places = new HashSet<>();
    
    // Constructors
    public Parking() {}
    
    public Parking(String nom, String adresse, Integer capacite) {
        this.nom = nom;
        this.adresse = adresse;
        this.capacite = capacite;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public Integer getCapacite() {
        return capacite;
    }
    
    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public Double getTarifHoraire() {
        return tarifHoraire;
    }
    
    public void setTarifHoraire(Double tarifHoraire) {
        this.tarifHoraire = tarifHoraire;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Set<Place> getPlaces() {
        return places;
    }
    
    public void setPlaces(Set<Place> places) {
        this.places = places;
    }
    
    public long getPlacesLibres() {
        return places.stream().filter(p -> p.getStatut() == PlaceStatut.LIBRE).count();
    }
    
    public long getPlacesOccupees() {
        return places.stream().filter(p -> p.getStatut() == PlaceStatut.OCCUPEE).count();
    }
    
    @Override
    public String toString() {
        return "Parking{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", capacite=" + capacite +
                '}';
    }
}
