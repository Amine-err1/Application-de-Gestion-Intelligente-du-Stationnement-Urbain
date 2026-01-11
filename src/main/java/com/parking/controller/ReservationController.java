package com.parking.controller;

import com.parking.dao.ReservationDAO;
import com.parking.dao.UserDAO;
import com.parking.dao.PlaceDAO;
import com.parking.model.Reservation;
import com.parking.model.ReservationStatut;
import com.parking.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationController {
    
    @FXML
    private TableView<Reservation> reservationTable;
    
    @FXML
    private TableColumn<Reservation, Integer> idColumn;
    
    @FXML
    private TableColumn<Reservation, String> userColumn;
    
    @FXML
    private TableColumn<Reservation, String> placeColumn;
    
    @FXML
    private TableColumn<Reservation, String> dateDebatColumn;
    
    @FXML
    private TableColumn<Reservation, String> dateFinColumn;
    
    @FXML
    private TableColumn<Reservation, String> statutColumn;
    
    @FXML
    private ComboBox<ReservationStatut> statutCombo;
    
    private ReservationDAO reservationDAO = new ReservationDAO();
    private UserDAO userDAO = new UserDAO();
    
    @FXML
    public void initialize() {
        setupTableColumns();
        setupComboBoxes();
        loadReservations();
    }
    
    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        userColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUser().getNom()));
        placeColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPlace().getNumero()));
        dateDebatColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDateDebut().toString()));
        dateFinColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDateFin().toString()));
        statutColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatut().toString()));
    }
    
    private void setupComboBoxes() {
        statutCombo.setItems(FXCollections.observableArrayList(ReservationStatut.values()));
    }
    
    private void loadReservations() {
        try {
            List<Reservation> reservations = reservationDAO.findAll();
            reservationTable.setItems(FXCollections.observableArrayList(reservations));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleUpdateStatut() {
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        
        if (selectedReservation == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une réservation");
            return;
        }
        
        if (statutCombo.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez sélectionner un statut");
            return;
        }
        
        try {
            reservationDAO.updateStatut(selectedReservation.getId(), statutCombo.getValue());
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Statut mis à jour avec succès");
            loadReservations();
            statutCombo.setValue(null);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleDeleteReservation() {
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        
        if (selectedReservation == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une réservation");
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Êtes-vous sûr de vouloir annuler cette réservation?");
        
        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            try {
                reservationDAO.delete(selectedReservation.getId());
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Réservation annulée avec succès");
                loadReservations();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'annulation: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void handleSelectReservation() {
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        
        if (selectedReservation != null) {
            statutCombo.setValue(selectedReservation.getStatut());
        }
    }
    
    @FXML
    private void handleRefreshReservations() {
        loadReservations();
        statutCombo.setValue(null);
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
