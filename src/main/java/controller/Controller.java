package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public final class Controller {
    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private Button addFriendsButton;

    public void logout() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Invalid credentials");
        alert.showAndWait();
    }

    public void addFriends() {
        new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add friends?").showAndWait();
    }
}
