package lk.ijse.hms_hibernet.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

public class Navigation {
    public static AnchorPane pneContainer;

    private static Stage stage;

    public static void init(AnchorPane pneContainer){
        Navigation.pneContainer=pneContainer;
    }

    public static void navigation(AnchorPane pneContainer,Route route) throws IOException {

        Navigation.pneContainer=pneContainer;
//        Stage window = (Stage)Navigation.pneContainer.getScene().getWindow();
        URL resource=null;
        switch (route){
            case MAIN:
                resource= Navigation.class.getClass().getResource("lk/ijse/hms_hibernet/view/MainWindow.fxml");
                AnchorPane load = FXMLLoader.load(resource);
                pneContainer.getChildren().clear();
                pneContainer.getChildren().add(load);
                break;
            case ROOM:
                initUI("RoomManager");
                break;
            case STUDENT:
                initUI("StudentManager");
                break;
            case RESERVATION:
                initUI("ReservationWindow");
                break;
            case REMAINING:
                initUI("Res_Stu_Window");
                break;
            case RESERVED:
                initUI("ReservationMakeStudentWindow");
                break;
        }


    }

    public static void initUI(String location) throws IOException {
        Navigation.pneContainer.getChildren().clear();
//        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.
//                class.getResource("/lk/ijse/the_car_guys/view/" + location)));
        Navigation.pneContainer.getChildren().
                add(FXMLLoader.load(setRoot(location)));
    }



    public static void setUi(Route route, Node node) throws IOException {
        stage = (Stage)node.getScene().getWindow();

        switch (route) {
            case LOGIN:
                setUi("LoginWindow");
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "UI Not Found!").show();
        }

    }
    public static void setUi(String location) throws IOException {
        AnchorPane load = FXMLLoader.load(setRoot(location));
        stage.setScene(new Scene(load));
        stage.centerOnScreen();
    }

    private static URL setRoot(String location) {
        return Navigation.class.
                getResource("../view/"+location+".fxml");
    }


}
