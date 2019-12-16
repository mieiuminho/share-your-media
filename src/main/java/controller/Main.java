package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import exceptions.LackOfPermissions;

import model.MediaCenter;
import model.MediaTableRow;

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
    private TextField nameUploadTextField;

    @FXML
    private TextField artistUploadTextField;

    @FXML
    private TextField albumUploadTextField;

    @FXML
    private TextField seriesUploadTextField;

    @FXML
    private Button uploadButton;

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
    private TableView<MediaTableRow> musicTable;

    private MediaPlayer mediaPlayer;

    private String playingSong;

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
        if (this.mediaPlayer != null)
            this.mediaPlayer.stop();
        musicTable.getItems().clear();
        helper.redirectTo("welcome");
    }

    @FXML
    public void changeCellAlbum(final CellEditEvent cellEditEvent) {
        String oldValue = cellEditEvent.getOldValue().toString();
        MediaTableRow row = musicTable.getSelectionModel().getSelectedItem();
        row.setAlbum(cellEditEvent.getNewValue().toString());

        try {
            model.addMedia(row);
        } catch (LackOfPermissions e) {
            row.setAlbum(oldValue);
            musicTable.refresh();
            helper.error("Authentication Error", e.getMessage());
        }
    }

    @FXML
    public void changeCellSeries(final CellEditEvent cellEditEvent) {
        String oldValue = cellEditEvent.getOldValue().toString();
        MediaTableRow row = musicTable.getSelectionModel().getSelectedItem();
        row.setSeries(cellEditEvent.getNewValue().toString());

        try {
            model.addMedia(row);
        } catch (LackOfPermissions e) {
            row.setSeries(oldValue);
            musicTable.refresh();
            helper.error("Authentication Error", e.getMessage());
        }
    }

    @FXML
    public void changeCellCategory1(final CellEditEvent cellEditEvent) {
        String oldValue = cellEditEvent.getOldValue().toString();
        MediaTableRow row = musicTable.getSelectionModel().getSelectedItem();
        row.setCategory1(cellEditEvent.getNewValue().toString());

        try {
            model.addMedia(row);
        } catch (LackOfPermissions e) {
            row.setCategory1(oldValue);
            musicTable.refresh();
            helper.error("Authentication Error", e.getMessage());
        }
    }

    @FXML
    public void changeCellCategory2(final CellEditEvent cellEditEvent) {
        String oldValue = cellEditEvent.getOldValue().toString();
        MediaTableRow row = musicTable.getSelectionModel().getSelectedItem();
        row.setCategory2(cellEditEvent.getNewValue().toString());

        try {
            model.addMedia(row);
        } catch (LackOfPermissions e) {
            row.setCategory2(oldValue);
            musicTable.refresh();
            helper.error("Authentication Error", e.getMessage());
        }
    }

    @FXML
    public void changeCellCategory3(final CellEditEvent cellEditEvent) {
        String oldValue = cellEditEvent.getOldValue().toString();
        MediaTableRow row = musicTable.getSelectionModel().getSelectedItem();
        row.setCategory3(cellEditEvent.getNewValue().toString());

        try {
            model.addMedia(row);
        } catch (LackOfPermissions e) {
            row.setCategory3(oldValue);
            musicTable.refresh();
            helper.error("Authentication Error", e.getMessage());
        }
    }

    @FXML
    void search() {
        Set<MediaTableRow> rows = model.searchMediaByNameOrArtist(this.searchBar.getText());
        musicTable.getItems().clear();
        musicTable.getItems().addAll(rows);
        this.searchBar.clear();
        musicTable.setEditable(true);
    }

    @FXML
    void play(final ActionEvent event) {
        MediaTableRow selectedSong = musicTable.getSelectionModel().getSelectedItem();

        if (this.mediaPlayer == null && selectedSong == null) {
            return;
        }

        if (this.mediaPlayer != null) {
            if (selectedSong.getName().equals(playingSong)) {
                if (this.mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                    this.mediaPlayer.pause();
                    this.playButton.setText("▸");
                } else {
                    this.mediaPlayer.play();
                    this.playButton.setText("II");
                }
            } else {
                this.mediaPlayer.stop();
                String name = selectedSong.getName();
                String artist = selectedSong.getArtist();
                try {
                    String fileLocation = model.downloadMedia(name, artist);
                    this.mediaPlayer = new MediaPlayer(new Media(new File(fileLocation).toURI().toString()));
                    this.mediaPlayer.setAutoPlay(true);
                    this.videoPlayer.setMediaPlayer(mediaPlayer);
                    this.playingSong = name;
                    this.playButton.setText("II");
                } catch (IOException e) {
                    e.printStackTrace();
                    helper.error("Download error", e.getMessage());
                }
            }
        } else {
            String name = selectedSong.getName();
            String artist = selectedSong.getArtist();
            try {
                String fileLocation = model.downloadMedia(name, artist);
                this.mediaPlayer = new MediaPlayer(new Media(new File(fileLocation).toURI().toString()));
                this.mediaPlayer.setAutoPlay(true);
                this.videoPlayer.setMediaPlayer(mediaPlayer);
                this.playingSong = name;
                this.playButton.setText("II");
            } catch (IOException e) {
                e.printStackTrace();
                helper.error("Download error", e.getMessage());
            }
        }

        this.mediaPlayer.setOnEndOfMedia(() -> {
            this.playingSong = "";
            this.playButton.setText("▸");
        });
    }

    @FXML
    void upload(final ActionEvent event) {
        try {
            model.uploadMedia(nameUploadTextField.getText(), artistUploadTextField.getText(),
                    albumUploadTextField.getText(), seriesUploadTextField.getText(),
                    helper.selectFile("Choose media to upload"));
        } catch (IOException | LackOfPermissions e) {
            e.printStackTrace();
            helper.error("Upload error", e.getMessage());
        }
    }
}
