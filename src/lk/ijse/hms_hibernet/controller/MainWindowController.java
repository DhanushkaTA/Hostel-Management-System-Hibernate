package lk.ijse.hms_hibernet.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hms_hibernet.util.Navigation;
import lk.ijse.hms_hibernet.util.Route;

import java.io.IOException;

public class MainWindowController {
    public AnchorPane container;
    public JFXButton btnStuMan;
    public ImageView imgStudent;
    public JFXButton btnRoomRes;
    public ImageView imgRes;
    public JFXButton btnRoomMan;
    public ImageView imgRoom;
    public AnchorPane pneContext;
    public AnchorPane pneContainer;
    public JFXButton btnDashboard;
    public JFXButton btnRes_stu;
    public JFXButton btnR_ReservedStu;

    public void btnStuManOnAction(ActionEvent actionEvent) throws IOException {
        pneContainer.setVisible(false);
        Navigation.navigation(pneContext, Route.STUDENT);
    }

    public void btnRoomResOnAction(ActionEvent actionEvent) throws IOException {
        pneContainer.setVisible(false);
        Navigation.navigation(pneContext, Route.RESERVATION);
    }

    public void btnRoomManOnAction(ActionEvent actionEvent) throws IOException {
        pneContainer.setVisible(false);
        Navigation.navigation(pneContext, Route.ROOM);
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {
        pneContainer.setVisible(true);
        pneContext.getChildren().clear();
    }

    public void btnRes_stuOnAction(ActionEvent actionEvent) throws IOException {
        pneContainer.setVisible(false);
        Navigation.navigation(pneContext, Route.REMAINING);
    }

    public void btnR_ReservedStuOnAction(ActionEvent actionEvent) throws IOException {
        pneContainer.setVisible(false);
        Navigation.navigation(pneContext, Route.RESERVED);
    }
}
