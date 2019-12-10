package controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import model.MediaCenter;
import model.MediaFile;

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
    public void changeCellAlbum(CellEditEvent cellEditEvent) {
        MediaFile media = musicTable.getSelectionModel().getSelectedItem();
        media.setAlbum(cellEditEvent.getNewValue().toString());
        this.model.addMedia(media);
    }

    @FXML
    public void changeCellSeries(CellEditEvent cellEditEvent) {
        MediaFile media = musicTable.getSelectionModel().getSelectedItem();
        media.setSeries(cellEditEvent.getNewValue().toString());
        this.model.addMedia(media);
    }

    // Faltam as tags

    @FXML
    void search() {
        Set<MediaFile> result = model.searchMediaByNameOrArtist(this.searchBar.getText());
        musicTable.getItems().clear();
        // musicTable.refresh();
        musicTable.getItems().addAll(result);
        this.searchBar.clear();
        musicTable.setEditable(true);
    }

    @FXML
    void play(final ActionEvent event) {

        // String fileLocationName = musicTable.getSelectionModel().getSelectedItem().getName();
        // pedir ao model o fileLocation com o filename
        // String fileLocation = "target/classes/videos/puffer.mp4";
        String fileLocation = "target/classes/songs/lanacover.mp3";

        Media musicFile = new Media(new File(fileLocation).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(musicFile);
        mediaPlayer.setAutoPlay(true);
        videoPlayer.setMediaPlayer(mediaPlayer);
        // e.printStackTrace();
        // helper.error("Load Fail", "Couldn't play the requested media file.");
    }

}
