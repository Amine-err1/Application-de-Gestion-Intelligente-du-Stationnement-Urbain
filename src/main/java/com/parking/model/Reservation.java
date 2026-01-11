package com.parking.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "date_debut", nullable = false)
    private LocalDateTime dateDebut;
    
    @Column(name = "date_fin", nullable = false)
    private LocalDateTime dateFin;
    
    @Column(name = "statut")
    @Enumerated(EnumType.STRING)
    private ReservationStatut statut = ReservationStatut.ACTIVE;
    
    @Column(name = "prix_total")
    private Double prixTotal;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;
    
    // Constructors
    public Reservation() {}
    
    public Reservation(LocalDateTime dateDebut, LocalDateTime dateFin, User user, Place place) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.user = user;
        this.place = place;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public LocalDateTime getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public LocalDateTime getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }
    
    public ReservationStatut getStatut() {
        return statut;
    }
    
    public void setStatut(ReservationStatut statut) {
        this.statut = statut;
    }
    
    public Double getPrixTotal() {
        return prixTotal;
    }
    
    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Place getPlace() {
        return place;
    }
    
    public void setPlace(Place place) {
        this.place = place;
    }
    
    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", statut=" + statut +
                ", user=" + user.getNom() +
                ", place=" + place.getNumero() +
                '}';
    }
}
