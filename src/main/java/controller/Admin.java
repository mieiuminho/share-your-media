package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.MediaCenter;

public final class Admin {
    private static Helper helper;
    private static MediaCenter model;

    public static void init(final Helper hlpr, final MediaCenter mdl) {
        Admin.helper = hlpr;
        Admin.model = mdl;
    }

    @FXML
    private Button backButton;

    public void back() {
        model.logout();
        helper.redirectTo("welcome");
    }
}
