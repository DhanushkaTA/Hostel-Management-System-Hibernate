import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        URL resource = this.getClass().getResource("/lk/ijse/hms_hibernet/view/SplashScreen.fxml");
  //      URL resource = this.getClass().getResource("/lk/ijse/hms_hibernet/view/RoomManager.fxml");
        Parent window = FXMLLoader.load(resource);
        Image icon=new Image(getClass().getResourceAsStream("lk/ijse/hms_hibernet/assets/image/mountain-hostel-logo.jpg"));
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(window);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Splash Screen");
        primaryStage.centerOnScreen();

        primaryStage.show();
    }
}
