package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import model.MediaFile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.collections.ObservableList;

import java.io.File;

import model.MediaCenter;

public final class Main {
    private static Helper helper;
    private static MediaCenter model;

    public static void init(final Helper hlpr, final MediaCenter mdl) {
        Main.helper = hlpr;
        Main.model = mdl;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem logoutMenuItem;

    @FXML
    private Button addFriendsButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchBar;

    @FXML
    private MediaView videoPlayer;

    @FXML
    private Button playButton;

    @FXML
    private TableView<MediaFile> musicTable;

    @FXML
    void addFriends(final ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Feature in development");
        alert.setHeaderText("Can't add friends yet");
        alert.showAndWait();
    }

    @FXML
    void logout(final ActionEvent event) {
        model.logout();
        helper.redirectTo("welcome");
    }

    @FXML
    public void changeCellArtist(CellEditEvent editedCell) {
        MediaFile m3 = musicTable.getSelectionModel().getSelectedItem();
        m3.setArtist(editedCell.getNewValue().toString());
        // dizer ao model para dar update ao file
    }

    @FXML
    public void changeCellTitle(CellEditEvent editedCell) {
        MediaFile m3 = musicTable.getSelectionModel().getSelectedItem();
        m3.setName(editedCell.getNewValue().toString());
        // dizer ao model para dar update ao file
    }

    // Faltam as tags

    @FXML
    void search() {
        System.out.println(this.searchBar.getText());
        // ir ao model buscar as coisas
        // musicTable.getItems().addAll(modelOutput);
        this.searchBar.clear();
        musicTable.setEditable(true);
    }

    @FXML
    void play(final ActionEvent event) {

        // String fileLocationName = musicTable.getSelectionModel().getSelectedItem().getName();
        // pedir ao model o fileLocation com o filename
        String fileLocation = "target/classes/images/puffer.mp4";

        try {
            File file = new File(fileLocation);
            Media musicFile = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(musicFile);
            mediaPlayer.setAutoPlay(true);
            videoPlayer.setMediaPlayer(mediaPlayer);
        } catch (Exception e) {
            e.printStackTrace();
            // Se calhar ter um alerta?
            /*
             * Alert alert = new Alert(Alert.AlertType.ERROR); alert.setTitle("Error Loading");
             * alert.setHeaderText("Couldnt load the requested media File"); alert.showAndWait();
             */
        }

    }
}
