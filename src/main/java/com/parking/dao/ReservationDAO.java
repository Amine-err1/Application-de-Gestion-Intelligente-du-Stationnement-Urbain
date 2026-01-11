package com.parking.dao;

import com.parking.model.Reservation;
import com.parking.model.ReservationStatut;
import com.parking.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    
    private UserDAO userDAO = new UserDAO();
    private PlaceDAO placeDAO = new PlaceDAO();
    
    public void create(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (date_debut, date_fin, statut, prix_total, user_id, place_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, Timestamp.valueOf(reservation.getDateDebut()));
            stmt.setTimestamp(2, Timestamp.valueOf(reservation.getDateFin()));
            stmt.setString(3, reservation.getStatut().toString());
            stmt.setDouble(4, reservation.getPrixTotal());
            stmt.setInt(5, reservation.getUser().getId());
            stmt.setInt(6, reservation.getPlace().getId());
            
            stmt.executeUpdate();
        }
    }
    
    public Reservation findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToReservation(rs);
            }
        }
        return null;
    }
    
    public List<Reservation> findAll() throws SQLException {
        String sql = "SELECT * FROM reservations";
        List<Reservation> reservations = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }
    
    public List<Reservation> findByUser(Integer userId) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE user_id = ?";
        List<Reservation> reservations = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }
    
    public List<Reservation> findByPlace(Integer placeId) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE place_id = ?";
        List<Reservation> reservations = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, placeId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }
    
    public List<Reservation> findByStatut(ReservationStatut statut) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE statut = ?";
        List<Reservation> reservations = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, statut.toString());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }
    
    public void update(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservations SET date_debut = ?, date_fin = ?, statut = ?, prix_total = ?, user_id = ?, place_id = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, Timestamp.valueOf(reservation.getDateDebut()));
            stmt.setTimestamp(2, Timestamp.valueOf(reservation.getDateFin()));
            stmt.setString(3, reservation.getStatut().toString());
            stmt.setDouble(4, reservation.getPrixTotal());
            stmt.setInt(5, reservation.getUser().getId());
            stmt.setInt(6, reservation.getPlace().getId());
            stmt.setInt(7, reservation.getId());
            
            stmt.executeUpdate();
        }
    }
    
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM reservations WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public void updateStatut(Integer id, ReservationStatut statut) throws SQLException {
        String sql = "UPDATE reservations SET statut = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, statut.toString());
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
        }
    }
    
    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setDateDebut(rs.getTimestamp("date_debut").toLocalDateTime());
        reservation.setDateFin(rs.getTimestamp("date_fin").toLocalDateTime());
        reservation.setStatut(ReservationStatut.valueOf(rs.getString("statut")));
        reservation.setPrixTotal(rs.getDouble("prix_total"));
        
        try {
            reservation.setUser(userDAO.findById(rs.getInt("user_id")));
            reservation.setPlace(placeDAO.findById(rs.getInt("place_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return reservation;
    }
}
