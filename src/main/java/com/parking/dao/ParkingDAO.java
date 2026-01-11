package com.parking.dao;

import com.parking.model.Parking;
import com.parking.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingDAO {
    
    public void create(Parking parking) throws SQLException {
        String sql = "INSERT INTO parkings (nom, adresse, capacite, latitude, longitude, tarif_horaire, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, parking.getNom());
            stmt.setString(2, parking.getAdresse());
            stmt.setInt(3, parking.getCapacite());
            stmt.setDouble(4, parking.getLatitude());
            stmt.setDouble(5, parking.getLongitude());
            stmt.setDouble(6, parking.getTarifHoraire());
            stmt.setString(7, parking.getDescription());
            
            stmt.executeUpdate();
        }
    }
    
    public Parking findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM parkings WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToParking(rs);
            }
        }
        return null;
    }
    
    public List<Parking> findAll() throws SQLException {
        String sql = "SELECT * FROM parkings";
        List<Parking> parkings = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                parkings.add(mapResultSetToParking(rs));
            }
        }
        return parkings;
    }
    
    public List<Parking> findByNom(String nom) throws SQLException {
        String sql = "SELECT * FROM parkings WHERE nom LIKE ?";
        List<Parking> parkings = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nom + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                parkings.add(mapResultSetToParking(rs));
            }
        }
        return parkings;
    }
    
    public void update(Parking parking) throws SQLException {
        String sql = "UPDATE parkings SET nom = ?, adresse = ?, capacite = ?, latitude = ?, longitude = ?, tarif_horaire = ?, description = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, parking.getNom());
            stmt.setString(2, parking.getAdresse());
            stmt.setInt(3, parking.getCapacite());
            stmt.setDouble(4, parking.getLatitude());
            stmt.setDouble(5, parking.getLongitude());
            stmt.setDouble(6, parking.getTarifHoraire());
            stmt.setString(7, parking.getDescription());
            stmt.setInt(8, parking.getId());
            
            stmt.executeUpdate();
        }
    }
    
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM parkings WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public long countPlaceLibres(Integer parkingId) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM places WHERE parking_id = ? AND statut = 'LIBRE'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, parkingId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getLong("count");
            }
        }
        return 0;
    }
    
    private Parking mapResultSetToParking(ResultSet rs) throws SQLException {
        Parking parking = new Parking();
        parking.setId(rs.getInt("id"));
        parking.setNom(rs.getString("nom"));
        parking.setAdresse(rs.getString("adresse"));
        parking.setCapacite(rs.getInt("capacite"));
        parking.setLatitude(rs.getDouble("latitude"));
        parking.setLongitude(rs.getDouble("longitude"));
        parking.setTarifHoraire(rs.getDouble("tarif_horaire"));
        parking.setDescription(rs.getString("description"));
        return parking;
    }
}
