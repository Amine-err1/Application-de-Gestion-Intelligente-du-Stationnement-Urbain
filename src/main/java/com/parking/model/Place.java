package com.parking.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "places")
public class Place implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "numero", nullable = false)
    private String numero;
    
    @Column(name = "statut", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlaceStatut statut;
    
    @Column(name = "type_place")
    @Enumerated(EnumType.STRING)
    private TypePlace typPlace = TypePlace.NORMAL;
    
    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    private Parking parking;
    
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservations = new HashSet<>();
    
    // Constructors
    public Place() {}
    
    public Place(String numero, PlaceStatut statut, Parking parking) {
        this.numero = numero;
        this.statut = statut;
        this.parking = parking;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public PlaceStatut getStatut() {
        return statut;
    }
    
    public void setStatut(PlaceStatut statut) {
        this.statut = statut;
    }
    
    public TypePlace getTypPlace() {
        return typPlace;
    }
    
    public void setTypPlace(TypePlace typPlace) {
        this.typPlace = typPlace;
    }
    
    public Parking getParking() {
        return parking;
    }
    
    public void setParking(Parking parking) {
        this.parking = parking;
    }
    
    public Set<Reservation> getReservations() {
        return reservations;
    }
    
    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", statut=" + statut +
                ", parking=" + parking.getNom() +
                '}';
    }
}
