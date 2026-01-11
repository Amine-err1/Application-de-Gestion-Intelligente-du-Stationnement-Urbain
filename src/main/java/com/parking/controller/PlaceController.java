package com.parking.controller;

import com.parking.dao.PlaceDAO;
import com.parking.dao.ParkingDAO;
import com.parking.model.Place;
import com.parking.model.PlaceStatut;
import com.parking.model.Parking;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class PlaceController {
    
    @FXML
    private TableView<Place> placeTable;
    
    @FXML
    private TableColumn<Place, Integer> idColumn;
    
    @FXML
    private TableColumn<Place, String> numeroColumn;
    
    @FXML
    private TableColumn<Place, String> statutColumn;
    
    @FXML
    private TableColumn<Place, String> typColumn;
    
    @FXML
    private ComboBox<Parking> parkingCombo;
    
    @FXML
    private TextField numeroField;
    
    @FXML
    private ComboBox<PlaceStatut> statutCombo;
    
    @FXML
    private ComboBox<String> typeCombo;
    
    private PlaceDAO placeDAO = new PlaceDAO();
    private ParkingDAO parkingDAO = new ParkingDAO();
    
    @FXML
    public void initialize() {
        setupTableColumns();
        setupComboBoxes();
        loadPlaces();
    }
    
    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        numeroColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNumero()));
        statutColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatut().toString()));
        typColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTypPlace().toString()));
    }
    
    private void setupComboBoxes() {
        try {
            List<Parking> parkings = parkingDAO.findAll();
            parkingCombo.setItems(FXCollections.observableArrayList(parkings));
            
            statutCombo.setItems(FXCollections.observableArrayList(PlaceStatut.values()));
            typeCombo.setItems(FXCollections.observableArrayList(
                "NORMAL", "HANDICAPE", "RESERVE", "ELECTRIQUE"
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void loadPlaces() {
        try {
            List<Place> places = placeDAO.findAll();
            placeTable.setItems(FXCollections.observableArrayList(places));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleAddPlace() {
        if (!validateInput()) {
            return;
        }
        
        try {
            Place place = new Place();
            place.setNumero(numeroField.getText().trim());
            place.setStatut(statutCombo.getValue());
            place.setTypPlace(com.parking.model.TypePlace.valueOf(typeCombo.getValue()));
            place.setParking(parkingCombo.getValue());
            
            placeDAO.create(place);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Place ajoutée avec succès");
            clearFields();
            loadPlaces();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleUpdatePlace() {
        Place selectedPlace = placeTable.getSelectionModel().getSelectedItem();
        
        if (selectedPlace == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une place");
            return;
        }
        
        if (!validateInput()) {
            return;
        }
        
        try {
            selectedPlace.setNumero(numeroField.getText().trim());
            selectedPlace.setStatut(statutCombo.getValue());
            selectedPlace.setTypPlace(com.parking.model.TypePlace.valueOf(typeCombo.getValue()));
            selectedPlace.setParking(parkingCombo.getValue());
            
            placeDAO.update(selectedPlace);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Place mise à jour avec succès");
            loadPlaces();
            clearFields();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleDeletePlace() {
        Place selectedPlace = placeTable.getSelectionModel().getSelectedItem();
        
        if (selectedPlace == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une place");
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette place?");
        
        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            try {
                placeDAO.delete(selectedPlace.getId());
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Place supprimée avec succès");
                loadPlaces();
                clearFields();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void handleSelectPlace() {
        Place selectedPlace = placeTable.getSelectionModel().getSelectedItem();
        
        if (selectedPlace != null) {
            numeroField.setText(selectedPlace.getNumero());
            statutCombo.setValue(selectedPlace.getStatut());
            typeCombo.setValue(selectedPlace.getTypPlace().toString());
            parkingCombo.setValue(selectedPlace.getParking());
        }
    }
    
    private void clearFields() {
        numeroField.clear();
        statutCombo.setValue(null);
        typeCombo.setValue(null);
        parkingCombo.setValue(null);
    }
    
    private boolean validateInput() {
        if (numeroField.getText().trim().isEmpty() ||
            statutCombo.getValue() == null ||
            typeCombo.getValue() == null ||
            parkingCombo.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez remplir tous les champs");
            return false;
        }
        return true;
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
