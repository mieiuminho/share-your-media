import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MediaCenter;

import java.io.IOException;

public final class App extends Application {
    private Helper helper;
    private MediaCenter model;

    public App() throws IOException {
        this.helper = new Helper();
        this.model = new MediaCenter();
    }

    @Override
    public void init() throws Exception {
        super.init();
        Welcome.init(this.helper, this.model);
        Main.init(this.helper, this.model);
        Login.init(this.helper, this.model);
        Admin.init(this.helper, this.model);
    }

    @SuppressWarnings("checkstyle:FinalParameters")
    public void start(Stage stage) throws Exception {
        Helper.init(stage);
        stage.setTitle("Share Your Media");
        this.helper.redirectTo("welcome");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @SuppressWarnings("checkstyle:FinalParameters")
    public static void main(String[] args) {
        launch(args);
    }
}
