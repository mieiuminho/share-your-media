package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import exceptions.LackOfPermissions;

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
    public void changeCellAlbum(final CellEditEvent cellEditEvent) {
        MediaFile media = musicTable.getSelectionModel().getSelectedItem();
        media.setAlbum(cellEditEvent.getNewValue().toString());
        this.model.addMedia(media);
    }

    @FXML
    public void changeCellSeries(final CellEditEvent cellEditEvent) {
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
        try {
            String name = musicTable.getSelectionModel().getSelectedItem().getName();
            String artist = musicTable.getSelectionModel().getSelectedItem().getArtist();
            String fileLocation = model.downloadMedia(name, artist);

            MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File(fileLocation).toURI().toString()));
            mediaPlayer.setAutoPlay(true);
            videoPlayer.setMediaPlayer(mediaPlayer);
        } catch (IOException e) {
            e.printStackTrace();
            helper.error("Download error", e.getMessage());
        }
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
