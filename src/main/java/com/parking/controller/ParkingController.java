package com.parking.controller;

import com.parking.dao.ParkingDAO;
import com.parking.dao.PlaceDAO;
import com.parking.model.Parking;
import com.parking.model.Place;
import com.parking.model.PlaceStatut;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.List;

public class ParkingController {
    
    @FXML
    private TableView<Parking> parkingTable;
    
    @FXML
    private TableColumn<Parking, Integer> idColumn;
    
    @FXML
    private TableColumn<Parking, String> nomColumn;
    
    @FXML
    private TableColumn<Parking, String> adresseColumn;
    
    @FXML
    private TableColumn<Parking, Integer> capaciteColumn;
    
    @FXML
    private TableColumn<Parking, Long> placesLibresColumn;
    
    @FXML
    private TextField nomField;
    
    @FXML
    private TextField adresseField;
    
    @FXML
    private TextField capaciteField;
    
    @FXML
    private TextField tarifField;
    
    @FXML
    private TextArea descriptionArea;
    
    private ParkingDAO parkingDAO = new ParkingDAO();
    private PlaceDAO placeDAO = new PlaceDAO();
    private ObservableList<Parking> parkingList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        setupTableColumns();
        loadParkings();
    }
    
    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nomColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        adresseColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAdresse()));
        capaciteColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getCapacite()).asObject());
        placesLibresColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleLongProperty(cellData.getValue().getPlacesLibres()).asObject());
    }
    
    private void loadParkings() {
        try {
            List<Parking> parkings = parkingDAO.findAll();
            parkingList = FXCollections.observableArrayList(parkings);
            parkingTable.setItems(parkingList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des parkings: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleAddParking() {
        if (!validateInput()) {
            return;
        }
        
        try {
            Parking parking = new Parking();
            parking.setNom(nomField.getText().trim());
            parking.setAdresse(adresseField.getText().trim());
            parking.setCapacite(Integer.parseInt(capaciteField.getText().trim()));
            parking.setTarifHoraire(Double.parseDouble(tarifField.getText().trim()));
            parking.setDescription(descriptionArea.getText().trim());
            
            parkingDAO.create(parking);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Parking ajouté avec succès");
            clearFields();
            loadParkings();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleUpdateParking() {
        Parking selectedParking = parkingTable.getSelectionModel().getSelectedItem();
        
        if (selectedParking == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un parking");
            return;
        }
        
        if (!validateInput()) {
            return;
        }
        
        try {
            selectedParking.setNom(nomField.getText().trim());
            selectedParking.setAdresse(adresseField.getText().trim());
            selectedParking.setCapacite(Integer.parseInt(capaciteField.getText().trim()));
            selectedParking.setTarifHoraire(Double.parseDouble(tarifField.getText().trim()));
            selectedParking.setDescription(descriptionArea.getText().trim());
            
            parkingDAO.update(selectedParking);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Parking mis à jour avec succès");
            loadParkings();
            clearFields();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleDeleteParking() {
        Parking selectedParking = parkingTable.getSelectionModel().getSelectedItem();
        
        if (selectedParking == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un parking");
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce parking?");
        
        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            try {
                parkingDAO.delete(selectedParking.getId());
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Parking supprimé avec succès");
                loadParkings();
                clearFields();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void handleSelectParking() {
        Parking selectedParking = parkingTable.getSelectionModel().getSelectedItem();
        
        if (selectedParking != null) {
            nomField.setText(selectedParking.getNom());
            adresseField.setText(selectedParking.getAdresse());
            capaciteField.setText(String.valueOf(selectedParking.getCapacite()));
            tarifField.setText(String.valueOf(selectedParking.getTarifHoraire()));
            descriptionArea.setText(selectedParking.getDescription());
        }
    }
    
    @FXML
    private void handleManagePlaces() {
        Parking selectedParking = parkingTable.getSelectionModel().getSelectedItem();
        
        if (selectedParking == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un parking");
            return;
        }
        
        showPlaceManagementDialog(selectedParking);
    }
    
    private void showPlaceManagementDialog(Parking parking) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Gestion des places - " + parking.getNom());
        
        try {
            List<Place> places = placeDAO.findByParking(parking.getId());
            
            TableView<Place> placeTable = new TableView<>();
            
            TableColumn<Place, Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            
            TableColumn<Place, String> numeroCol = new TableColumn<>("Numéro");
            numeroCol.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNumero()));
            
            TableColumn<Place, String> statutCol = new TableColumn<>("Statut");
            statutCol.setCellValueFactory(cellData -> 
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatut().toString()));
            
            placeTable.getColumns().addAll(idCol, numeroCol, statutCol);
            placeTable.setItems(FXCollections.observableArrayList(places));
            
            dialog.getDialogPane().setContent(placeTable);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.showAndWait();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des places: " + e.getMessage());
        }
    }
    
    private void clearFields() {
        nomField.clear();
        adresseField.clear();
        capaciteField.clear();
        tarifField.clear();
        descriptionArea.clear();
    }
    
    private boolean validateInput() {
        if (nomField.getText().trim().isEmpty() ||
            adresseField.getText().trim().isEmpty() ||
            capaciteField.getText().trim().isEmpty() ||
            tarifField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez remplir tous les champs obligatoires");
            return false;
        }
        
        try {
            Integer.parseInt(capaciteField.getText().trim());
            Double.parseDouble(tarifField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Capacité et tarif doivent être des nombres");
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
