package com.parking.controller;

import com.parking.dao.UserDAO;
import com.parking.model.User;
import com.parking.model.UserRole;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class LoginController {
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    private UserDAO userDAO = new UserDAO();
    private User currentUser;
    
    public LoginController() {}
    
    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        
        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez remplir tous les champs");
            return;
        }
        
        try {
            User user = userDAO.findByEmail(email);
            
            if (user != null && user.getPassword().equals(password)) {
                currentUser = user;
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Connexion réussie!");
                // Navigation selon le rôle sera implémentée dans la vue principale
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Email ou mot de passe incorrect");
                passwordField.clear();
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de base de données: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleRegister() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        
        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Veuillez remplir tous les champs");
            return;
        }
        
        try {
            User existingUser = userDAO.findByEmail(email);
            
            if (existingUser != null) {
                showAlert(Alert.AlertType.WARNING, "Erreur", "Cet email est déjà utilisé");
                return;
            }
            
            User newUser = new User();
            newUser.setNom("Utilisateur");
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setRole(UserRole.USER);
            
            userDAO.create(newUser);
            
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription réussie! Vous pouvez maintenant vous connecter");
            emailField.clear();
            passwordField.clear();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'inscription: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleClear() {
        emailField.clear();
        passwordField.clear();
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
