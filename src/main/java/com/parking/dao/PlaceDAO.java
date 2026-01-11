package com.parking.dao;

import com.parking.model.Place;
import com.parking.model.PlaceStatut;
import com.parking.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceDAO {
    
    private ParkingDAO parkingDAO = new ParkingDAO();
    
    public void create(Place place) throws SQLException {
        String sql = "INSERT INTO places (numero, statut, type_place, parking_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, place.getNumero());
            stmt.setString(2, place.getStatut().toString());
            stmt.setString(3, place.getTypPlace().toString());
            stmt.setInt(4, place.getParking().getId());
            
            stmt.executeUpdate();
        }
    }
    
    public Place findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM places WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPlace(rs);
            }
        }
        return null;
    }
    
    public List<Place> findAll() throws SQLException {
        String sql = "SELECT * FROM places";
        List<Place> places = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                places.add(mapResultSetToPlace(rs));
            }
        }
        return places;
    }
    
    public List<Place> findByParking(Integer parkingId) throws SQLException {
        String sql = "SELECT * FROM places WHERE parking_id = ?";
        List<Place> places = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, parkingId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                places.add(mapResultSetToPlace(rs));
            }
        }
        return places;
    }
    
    public List<Place> findByParkingAndStatut(Integer parkingId, PlaceStatut statut) throws SQLException {
        String sql = "SELECT * FROM places WHERE parking_id = ? AND statut = ?";
        List<Place> places = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, parkingId);
            stmt.setString(2, statut.toString());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                places.add(mapResultSetToPlace(rs));
            }
        }
        return places;
    }
    
    public void update(Place place) throws SQLException {
        String sql = "UPDATE places SET numero = ?, statut = ?, type_place = ?, parking_id = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, place.getNumero());
            stmt.setString(2, place.getStatut().toString());
            stmt.setString(3, place.getTypPlace().toString());
            stmt.setInt(4, place.getParking().getId());
            stmt.setInt(5, place.getId());
            
            stmt.executeUpdate();
        }
    }
    
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM places WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public void updateStatut(Integer id, PlaceStatut statut) throws SQLException {
        String sql = "UPDATE places SET statut = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, statut.toString());
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
        }
    }
    
    private Place mapResultSetToPlace(ResultSet rs) throws SQLException {
        Place place = new Place();
        place.setId(rs.getInt("id"));
        place.setNumero(rs.getString("numero"));
        place.setStatut(PlaceStatut.valueOf(rs.getString("statut")));
        place.setTypPlace(com.parking.model.TypePlace.valueOf(rs.getString("type_place")));
        
        try {
            place.setParking(parkingDAO.findById(rs.getInt("parking_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return place;
    }
}
