import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class App extends Application {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 800;

    @SuppressWarnings("checkstyle:FinalParameters")
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/main.fxml"));
        primaryStage.setTitle("Share Your Media");
        primaryStage.setScene(new Scene(root, App.WINDOW_WIDTH, App.WINDOW_HEIGHT));
        primaryStage.show();
    }

    @SuppressWarnings("checkstyle:FinalParameters")
    public static void main(String[] args) {
        launch(args);
    }
}
