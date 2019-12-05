package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import model.MediaCenter;

public final class Main {
    private static Helper helper;
    private static MediaCenter model;

    public static void init(final Helper hlpr, final MediaCenter mdl) {
        Main.helper = hlpr;
        Main.model = mdl;
    }

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private Button addFriendsButton;

    @FXML
    void addFriends(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText("Invalid credentials");
        alert.showAndWait();
    }

    @FXML
    void logout(ActionEvent event) {
        model.logout();
        helper.redirectTo("welcome");
    }

}
