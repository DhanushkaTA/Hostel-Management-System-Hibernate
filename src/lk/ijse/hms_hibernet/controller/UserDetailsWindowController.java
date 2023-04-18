package lk.ijse.hms_hibernet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.hms_hibernet.dto.UserDTO;
import lk.ijse.hms_hibernet.service.ServiceFactory;
import lk.ijse.hms_hibernet.service.ServiceTypes;
import lk.ijse.hms_hibernet.service.custome.UserService;
import lk.ijse.hms_hibernet.service.exception.NotFoundException;
import lk.ijse.hms_hibernet.util.AnimeTypes;
import lk.ijse.hms_hibernet.util.AnimeUtil;
import lk.ijse.hms_hibernet.util.ValidationUtil;

import java.util.regex.Pattern;

public class UserDetailsWindowController {
    public AnchorPane context;
    public JFXButton btnClose;
    public JFXButton btnMinimize;
    public TextField txtUsername;
    public JFXButton btnUpdate;
    public Label lblUsername;
    public PasswordField txtPass_Labal;
    public TextField txtNewPassword;
    public PasswordField ptxtNewPassword;
    public JFXButton btnVisible;
    public JFXButton btnSetHidden;
    public JFXButton btnCancel;
    public Label lblUsername2;
    public Text lblName;
    public Text lblPhoneNumber;
    public JFXCheckBox chkBoxUsername;
    public JFXCheckBox chkBoxPassword;
    public Label lblE_username;
    public Label lblE_password;

    public static AnchorPane pneContainerWindow=null;

    public static UserDTO userDTO=null;

    public static DashboardWindowController dashboardWindowController=null;

    private Pattern passwordPattern;

    private static final UserService userService=
            (UserService) ServiceFactory.getServiceFactory().getService(ServiceTypes.USER);
    public Label lblE_Oldpassword;
    public TextField txtOldPassword;
    public PasswordField ptxtOldPassword;

    public void initialize(){
        setUserDetails();
        setFields2(false);
        setFields(false);
        passwordPattern=Pattern.compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
    }

    private void setUserDetails() {
        UserDTO userDetails = userService.getUserDetails(userDTO.getUsername());
        lblUsername.setText(userDetails.getUsername());
        lblUsername2.setText(userDetails.getUsername());
        txtPass_Labal.setText(userDetails.getPassword());
        lblName.setText(userDetails.getUser_name().getF_name()+" "+userDetails.getUser_name().getL_name());
        lblPhoneNumber.setText(userDetails.getPhone_number());

        txtPass_Labal.setText(userDetails.getPassword());
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        context.getScene().getWindow().hide();
        pneContainerWindow.setEffect(null);
        dashboardWindowController.lblUsername.setText(userDTO.getUsername());
        DashboardWindowController.user=userDTO;
    }

    public void btnMinimizeOnAction(ActionEvent actionEvent) {
//        Stage stage=(Stage)
//                ((Node)actionEvent.getSource()).getScene().getWindow();
//        stage.setIconified(true);
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).setIconified(true);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        if(chkBoxPassword.isSelected()){
            if (validationPassword()){
                updateUser();
                //------------------
                AnimeUtil.addCss(AnimeTypes.CORRECT,txtNewPassword);
                AnimeUtil.addCss(AnimeTypes.CORRECT,ptxtNewPassword);
                AnimeUtil.removeCss(AnimeTypes.WRONG,txtNewPassword);
                AnimeUtil.removeCss(AnimeTypes.WRONG,ptxtNewPassword);

            }else {
                String message="The password policy is:\n" +
                        "\n" +
                        "At least 8 chars\n" +
                        "\n" +
                        "Contains at least one digit\n" +
                        "\n" +
                        "Contains at least one lower alpha char and one upper alpha char\n" +
                        "\n" +
                        "Contains at least one char within a set of special chars (@#%$^ etc.)\n" +
                        "\n" +
                        "Does not contain space, tab, etc";
                new Alert(Alert.AlertType.ERROR,message).show();
                //-------------------------------------
                AnimeUtil.setAnime(AnimeTypes.SHAKE,txtNewPassword);
                AnimeUtil.setAnime(AnimeTypes.SHAKE,ptxtNewPassword);
                AnimeUtil.removeCss(AnimeTypes.CORRECT,txtNewPassword);
                AnimeUtil.removeCss(AnimeTypes.CORRECT,ptxtNewPassword);
                AnimeUtil.addCss(AnimeTypes.WRONG,txtNewPassword);
                AnimeUtil.addCss(AnimeTypes.WRONG,ptxtNewPassword);
            }
        }else {
            updateUser();
        }

    }

    private void updateUser() {
        UserDTO updateUser=new UserDTO(
                userDTO.getUid(),
                userDTO.getUser_name(),
                lblUsername.getText(),
                txtPass_Labal.getText(),
                lblPhoneNumber.getText()
        );

        if (chkBoxUsername.isSelected()){
            updateUser.setUsername(txtUsername.getText());
        }

        if (chkBoxPassword.isSelected()){
            updateUser.setPassword(txtNewPassword.getText());
        }


        try {
            boolean isUpdated = userService.updateUser(updateUser);
            if (isUpdated){
                userDTO=updateUser;
                new Alert(Alert.AlertType.INFORMATION,"User Updated").show();
                setUserDetails();
            }else {
                new Alert(Alert.AlertType.ERROR,"User not Updated").show();
            }
        }catch (NotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnVisibleOnAction(ActionEvent actionEvent) {
        btnVisible.setVisible(false);
        btnSetHidden.setVisible(true);
        txtNewPassword.setVisible(true);
        ptxtNewPassword.setVisible(false);
        txtOldPassword.setVisible(true);
        ptxtOldPassword.setVisible(false);
    }

    public void btnSetHiddenOnAction(ActionEvent actionEvent) {
        btnVisible.setVisible(true);
        btnSetHidden.setVisible(false);
        txtNewPassword.setVisible(false);
        ptxtNewPassword.setVisible(true);
        txtOldPassword.setVisible(false);
        ptxtOldPassword.setVisible(true);
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        btnCloseOnAction(actionEvent);
    }

    public void chkBoxUsernameOnAction(ActionEvent actionEvent) {
        if (chkBoxUsername.isSelected()){
            setFields(true);
        }else {
            setFields(false);
        }
    }

    public void chkBoxPasswordOnAction(ActionEvent actionEvent) {
        if (chkBoxPassword.isSelected()){
            setFields2(true);
        }else {
            setFields2(false);
        }
    }

    private void setFields(boolean action){
        txtUsername.setVisible(action);
        lblE_username.setVisible(action);
    }

    private void setFields2(boolean action){
        txtNewPassword.setVisible(action);
        ptxtNewPassword.setVisible(action);
        btnSetHidden.setVisible(action);
        btnVisible.setVisible(action);
        lblE_password.setVisible(action);
        lblE_Oldpassword.setVisible(action);
        txtOldPassword.setVisible(action);
        ptxtOldPassword.setVisible(action);
    }

    public void txtNewPasswordOnAction(KeyEvent keyEvent) {
        ptxtNewPassword.setText(txtNewPassword.getText());
    }

    public void ptxtNewPasswordOnAction(KeyEvent keyEvent) {
        txtNewPassword.setText(ptxtNewPassword.getText());
    }

    public void txtOldPasswordOnAction(KeyEvent keyEvent) {
        ptxtOldPassword.setText(txtOldPassword.getText());
        validation();
    }

    public void ptxtOldPasswordOnAction(KeyEvent keyEvent) {
        txtOldPassword.setText(ptxtOldPassword.getText());
        validation();
    }

    private void validation() {
        if(txtOldPassword.getText().equals(txtPass_Labal.getText())){
            btnUpdate.setDisable(false);
            AnimeUtil.removeCss(AnimeTypes.WRONG,txtOldPassword);
            AnimeUtil.addCss(AnimeTypes.CORRECT,txtOldPassword);
            AnimeUtil.removeCss(AnimeTypes.WRONG,ptxtOldPassword);
            AnimeUtil.addCss(AnimeTypes.CORRECT,ptxtOldPassword);
        }else {
            btnUpdate.setDisable(true);
            AnimeUtil.removeCss(AnimeTypes.CORRECT,txtOldPassword);
            AnimeUtil.removeCss(AnimeTypes.CORRECT,ptxtOldPassword);
            AnimeUtil.addCss(AnimeTypes.WRONG,txtOldPassword);
            AnimeUtil.addCss(AnimeTypes.WRONG,ptxtOldPassword);
        }
    }

    private boolean validationPassword(){
        if (chkBoxPassword.isSelected()){
            if (ValidationUtil.validation(passwordPattern,txtNewPassword)){
                return true;
            }
        }
        return false;
    }


}
