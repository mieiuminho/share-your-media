package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class Controller {
    @FXML
    public MenuItem mni_logout;

    @FXML
    public Button btn_add_friends;

    public void logout() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Invalid credentials");
        alert.getDialogPane().setPrefSize(480, 320);
        alert.showAndWait();
    }

    public void addFriends() {
        new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add friends?").showAndWait();
    }
}
