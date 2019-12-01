package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.MediaCenter;

public final class Login {
    private static Helper helper;
    private static MediaCenter model;

    public static void init(final Helper hlpr, final MediaCenter mdl) {
        Login.helper = hlpr;
        Login.model = mdl;
    }

    @FXML
    private Button backButton;

    public void back() {
        helper.redirectTo("welcome");
    }
}
