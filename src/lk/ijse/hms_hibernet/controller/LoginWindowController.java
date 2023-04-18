package lk.ijse.hms_hibernet.controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hms_hibernet.dto.LoginDataDTO;
import lk.ijse.hms_hibernet.dto.UserDTO;
import lk.ijse.hms_hibernet.service.ServiceFactory;
import lk.ijse.hms_hibernet.service.ServiceTypes;
import lk.ijse.hms_hibernet.service.custome.LoginDataService;
import lk.ijse.hms_hibernet.service.custome.UserService;
import lk.ijse.hms_hibernet.service.exception.DuplicateException;
import lk.ijse.hms_hibernet.util.Navigation;
import lk.ijse.hms_hibernet.util.TimeUtil;

import java.io.IOException;

public class LoginWindowController {
    public TextField txtUsername;
    public TextField txtPassword;
    public PasswordField pwtxtPassword;
    public JFXButton btnVisible;
    public JFXButton btnSetHidden;
    public JFXButton btnLogIn;
    public Label txtWelcome;

    public static final UserService userService=
            (UserService) ServiceFactory.getServiceFactory().getService(ServiceTypes.USER);

    public static final LoginDataService loginService=
            (LoginDataService) ServiceFactory.getServiceFactory().getService(ServiceTypes.LOGIN);

    public void initialize() {
        new ZoomIn(txtWelcome).play();
        btnSetHidden.setVisible(false);
        txtPassword.setVisible(false);
        txtUsername.clear();
        txtPassword.clear();
        pwtxtPassword.clear();
    }

    public void txtPasswordOnAction(KeyEvent keyEvent) {
        pwtxtPassword.setText(txtPassword.getText());
    }

    public void pwtxtPasswordOnAction(KeyEvent keyEvent) {
        txtPassword.setText(pwtxtPassword.getText());
    }

    public void btnVisibleOnAction(ActionEvent actionEvent) {
        btnVisible.setVisible(false);
        btnSetHidden.setVisible(true);
        txtPassword.setVisible(true);
        pwtxtPassword.setVisible(false);
    }

    public void btnSetHiddenOnAction(ActionEvent actionEvent) {
        btnVisible.setVisible(true);
        btnSetHidden.setVisible(false);
        txtPassword.setVisible(false);
        pwtxtPassword.setVisible(true);
        btnVisible.requestFocus();
    }

    public void btnLogInOnAction(ActionEvent actionEvent) {
        if(!txtUsername.getText().isEmpty()){
            if(!txtPassword.getText().isEmpty()){
                UserDTO userDetails = userService.getUserDetails(txtUsername.getText());
                if(null!=userDetails){
                    if((txtPassword.getText()).equals(userDetails.getPassword())){
                        DashboardWindowController.user=userDetails;
                        DashboardWindowController.loginDate= TimeUtil.getDate();
                        DashboardWindowController.loginTime=TimeUtil.getTime();
                        addLoginDetails(userDetails);
                    }else {
                        new Shake(txtPassword).play();
                        new Shake(pwtxtPassword).play();
                    }
                }else {
                    new Shake(txtUsername).play();
                }
            }else {
                new Shake(txtPassword).play();
                new Shake(pwtxtPassword).play();
            }
        }else {
            new Shake(txtUsername).play();
        }


    }

    private void addLoginDetails(UserDTO userDTO) {
        String nextLoginId = loginService.getNextLoginId();

        try {
            LoginDataDTO loginDataDTO = new LoginDataDTO(
                    nextLoginId,
                    TimeUtil.getDate(), TimeUtil.getTime(),
                    "Not yet", "Not yet", "LogIn", userDTO);
            boolean isAdded = loginService.saveLoginDetails(loginDataDTO);
            if(isAdded){
                DashboardWindowController.loginDataDTO=loginDataDTO;
                goToDashboard();
            }else {
                new Alert(Alert.AlertType.ERROR,"Login not success. Something is wrong!").show();
            }

        }catch (DuplicateException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void goToDashboard() {

        try {
            AnchorPane root = FXMLLoader.load(getClass().
                    getResource("../view/DashboardWindow.fxml"));

            AnchorPane mainWindow = FXMLLoader.load(getClass().
                    getResource("../view/MainWindow.fxml"));

            Stage primaryStage=new Stage();

            Image icon=new Image("lk/ijse/hms_hibernet/assets/image/mountain-hostel-logo.jpg");
            primaryStage.getIcons().add(icon);

            AnchorPane pneContainer = (AnchorPane) root.lookup("#pneContainer");

            Navigation.init(pneContainer);

            pneContainer.getChildren().add(mainWindow);

            Stage window = (Stage) btnLogIn.getScene().getWindow();
            window.setScene(new Scene(root));

            window.centerOnScreen();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
