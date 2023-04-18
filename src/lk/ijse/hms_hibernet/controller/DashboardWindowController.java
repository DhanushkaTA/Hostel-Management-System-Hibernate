package lk.ijse.hms_hibernet.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import lk.ijse.hms_hibernet.dto.LoginDataDTO;
import lk.ijse.hms_hibernet.dto.UserDTO;
import lk.ijse.hms_hibernet.entity.User;
import lk.ijse.hms_hibernet.service.ServiceFactory;
import lk.ijse.hms_hibernet.service.ServiceTypes;
import lk.ijse.hms_hibernet.service.custome.LoginDataService;
import lk.ijse.hms_hibernet.service.exception.NotFoundException;
import lk.ijse.hms_hibernet.util.Navigation;
import lk.ijse.hms_hibernet.util.Route;
import lk.ijse.hms_hibernet.util.TimeUtil;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardWindowController {
    public AnchorPane pneContainerWindow;
    public AnchorPane pneContainer;
    public JFXButton btnLogout;
    public JFXButton btnUpdate;
    public Label lblDate;
    public Label lblTime;
    public Text lblUsername;

    public static UserDTO user=null;
    public static String loginDate;
    public static String loginTime;

    public static LoginDataDTO loginDataDTO;

    public static final LoginDataService loginService=
            (LoginDataService) ServiceFactory.getServiceFactory().getService(ServiceTypes.LOGIN);


    public void initialize(){
        setDateAndTime();
        lblUsername.setText(user.getUsername());
    }

    private void setDateAndTime() {
        Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
            lblTime.setText(LocalDateTime.now().format(formatter));

        }), new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

        lblDate.setText(TimeUtil.getDate());
    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        loginDataDTO.setLogOutDate(TimeUtil.getDate());
        loginDataDTO.setLogOutTime(TimeUtil.getTime());
        loginDataDTO.setStatus("LogOut");

        try {
            boolean isUpdated = loginService.updateLoginDetails(loginDataDTO);
            if (isUpdated){
                Navigation.setUi(Route.LOGIN,pneContainerWindow);
                new Alert(Alert.AlertType.INFORMATION,
                        "Login Details ID="+loginDataDTO.getLoginId()+" '\n' Logout Completed").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Logout not Completed!!!").show();
            }
        }catch (NotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        UserDetailsWindowController.userDTO=this.user;
        UserDetailsWindowController.dashboardWindowController=this;

        Stage stage=new Stage(StageStyle.UNDECORATED);
        URL resource = this.getClass().getResource("/lk/ijse/hms_hibernet/view/UserDetailsWindow.fxml");
        Parent window = FXMLLoader.load(resource);
        Scene scene = new Scene(window);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        pneContainerWindow.setEffect(new BoxBlur(5, 5, 5));

        UserDetailsWindowController.pneContainerWindow=pneContainerWindow;
    }
}
