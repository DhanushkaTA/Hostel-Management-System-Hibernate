package lk.ijse.hms_hibernet.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hms_hibernet.dto.LoginDataDTO;
import lk.ijse.hms_hibernet.service.ServiceFactory;
import lk.ijse.hms_hibernet.service.ServiceTypes;
import lk.ijse.hms_hibernet.service.custome.LoginDataService;
import lk.ijse.hms_hibernet.util.Navigation;

import java.io.IOException;

public class SplashScreenController {
    public AnchorPane container;
    public Rectangle rctCon;
    public Rectangle rctLoading;
    public Label lblLoading;

    public static final LoginDataService loginService=
            (LoginDataService) ServiceFactory.getServiceFactory().getService(ServiceTypes.LOGIN);

    public void initialize(){
        Timeline timeline=new Timeline();

        KeyFrame keyFrame1=new KeyFrame(Duration.millis(500), actionEvent -> {
            lblLoading.setText("Initialize Application");
            rctLoading.setWidth(rctCon.getWidth()*0.3);
        });

        KeyFrame keyFrame2=new KeyFrame(Duration.millis(1000),actionEvent -> {
            lblLoading.setText("Initialize internet Resource");
            rctLoading.setWidth(rctCon.getWidth()*0.5);
        });

        KeyFrame keyFrame3=new KeyFrame(Duration.millis(1500),actionEvent -> {
            lblLoading.setText("Initialize Images");
            rctLoading.setWidth(rctCon.getWidth()*0.6);
        });

        KeyFrame keyFrame4=new KeyFrame(Duration.millis(2000),actionEvent -> {
            lblLoading.setText("Initialize Uis");
            rctLoading.setWidth(rctCon.getWidth()*0.8);
        });

        KeyFrame keyFrame5=new KeyFrame(Duration.millis(2500),actionEvent -> {
            lblLoading.setText("Getting Started....");
            rctLoading.setWidth(rctCon.getWidth()*0.9);
        });

        KeyFrame keyFrame6=new KeyFrame(Duration.millis(3000),actionEvent -> {
            lblLoading.setText("Welcome to ");
            rctLoading.setWidth(rctCon.getWidth());
        });

        KeyFrame keyFrame7=new KeyFrame(Duration.millis(3500),actionEvent -> {

            String location="LoginWindow";
            String status =null;
            AnchorPane pneContainer =null;

            LoginDataDTO lastLoginDetails = loginService.getLastLoginDetails();

            if (null!=lastLoginDetails){
                status = lastLoginDetails.getStatus();
                if(status.equals("LogIn")){
                    DashboardWindowController.user=lastLoginDetails.getUserDTO();
                    DashboardWindowController.loginDataDTO=lastLoginDetails;
                    location= "DashboardWindow";


                }
            }


            Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().
                            getResource("../view/"+location+".fxml"));

                    if(status.equals("LogIn")){
                        AnchorPane mainWindow = FXMLLoader.load(getClass().
                                getResource("../view/MainWindow.fxml"));

                        pneContainer = (AnchorPane) root.lookup("#pneContainer");
                        Navigation.init(pneContainer);
                        pneContainer.getChildren().add(mainWindow);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                Stage primaryStage=new Stage();

                Image icon=new Image("lk/ijse/hms_hibernet/assets/image/mountain-hostel-logo.jpg");
                primaryStage.getIcons().add(icon);

                primaryStage.setScene(new Scene(root));
                primaryStage.centerOnScreen();
                primaryStage.show();

                container.getScene().getWindow().hide();

        });

        timeline.getKeyFrames().addAll(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5,keyFrame6,keyFrame7);
        timeline.playFromStart();
    }

}

