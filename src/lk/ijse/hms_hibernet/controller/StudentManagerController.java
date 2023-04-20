package lk.ijse.hms_hibernet.controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hms_hibernet.dto.StudentDTO;
import lk.ijse.hms_hibernet.embeded.Cust_name;
import lk.ijse.hms_hibernet.service.ServiceFactory;
import lk.ijse.hms_hibernet.service.ServiceTypes;
import lk.ijse.hms_hibernet.service.custome.StudentService;
import lk.ijse.hms_hibernet.service.exception.DuplicateException;
import lk.ijse.hms_hibernet.service.exception.NotFoundException;
import lk.ijse.hms_hibernet.tm.StudentTm;
import lk.ijse.hms_hibernet.util.AnimeTypes;
import lk.ijse.hms_hibernet.util.AnimeUtil;
import lk.ijse.hms_hibernet.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class StudentManagerController {
    public AnchorPane container;
    public TableView tblStudent;
    public TableColumn clmStudentId;
    public TableColumn clmF_name;
    public TableColumn clmL_name;
    public TableColumn clmAddress;
    public TableColumn clmPhone;
    public TableColumn clmDob;
    public TableColumn clmGender;
    public TextField txtStudentId;
    public TextField txtF_name;
    public TextField txtL_name;
    public TextField txtDob;
    public JFXButton btnAdd;
    public JFXButton btnReset;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public TextField txtAddress;
    public ToggleGroup gender;
    public JFXRadioButton rBtnFemale;
    public JFXRadioButton rBtnMale;
    public TextField txtPhone;
    public JFXButton btnSelect;
    public TextField txtGender;
    public JFXButton btnClear;
    public JFXTextField txtSearch;
    private Pattern txtTelephonePattern;
    private Pattern dobPattern;
    private Pattern namePattern;
    private Pattern idPattern;
    private Pattern addressPattern;
    public List<Pattern>patternList;
    public List<TextField>textFieldList;

    private String olID;

    private static final StudentService studentService=
            (StudentService) ServiceFactory.getServiceFactory().getService(ServiceTypes.STUDENT);

    public void initialize(){
        btnReset.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        clmStudentId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        clmF_name.setCellValueFactory(new PropertyValueFactory<>("F_name"));
        clmL_name.setCellValueFactory(new PropertyValueFactory<>("L_name"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        clmDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        clmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        loadDataToTable();
        regex();
        patternList=new ArrayList<>();
        patternList.add(idPattern);
        patternList.add(namePattern);
        patternList.add(namePattern);
        patternList.add(dobPattern);
        patternList.add(addressPattern);
        patternList.add(txtTelephonePattern);

        textFieldList=new ArrayList<>();
        textFieldList.add(txtStudentId);
        textFieldList.add(txtF_name);
        textFieldList.add(txtL_name);
        textFieldList.add(txtDob);
        textFieldList.add(txtAddress);
        textFieldList.add(txtPhone);

    }

    public void regex() {
        txtTelephonePattern = Pattern.compile("^[0-9]{10,10}$");
        dobPattern=Pattern.compile("[0-9]{4,4}-[0-9]{2,2}-[0-9]{2,2}$");
        namePattern=Pattern.compile("^[(a-z)(A-Z)]{1,100}$");
        idPattern=Pattern.compile("^[(S)(0-9)]{10}$");
        addressPattern=Pattern.compile("[(,)(/)(0-9)(a-z)(A-Z)]{1,}$");
    }

    private void loadDataToTable() {
        List<StudentDTO> studentList = studentService.getStudentList();

        List<StudentTm> list=new ArrayList<>();

        for(StudentDTO studentDTO:studentList){
            list.add(
                    new StudentTm(
                            studentDTO.getSId(),
                            studentDTO.getName().getF_name(),
                            studentDTO.getName().getL_name(),
                            studentDTO.getAddress(),
                            studentDTO.getPhoneNum(),
                            studentDTO.getDob(),
                            studentDTO.getGender()));
        }
        ObservableList<StudentTm> obList=
                FXCollections.observableArrayList(list);
        tblStudent.setItems(obList);

        if (null!=obList){
            addActionToTextFields(obList);
        }
    }

    private void addActionToTextFields(ObservableList<StudentTm> obList) {
        FilteredList<StudentTm>filteredList=new FilteredList<>(obList,b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(studentTm -> {
                if (newValue.isEmpty() || null==newValue){
                    return true;
                }
                String searchKeyWord=newValue.toLowerCase();

                if (studentTm.getSId().toLowerCase().indexOf(searchKeyWord) > -1){
                    return true;
                } else if (studentTm.getF_name().toLowerCase().indexOf(searchKeyWord) > -1) {
                    return true;
                } else if (studentTm.getL_name().toLowerCase().indexOf(searchKeyWord) > -1) {
                    return true;
                } else if (studentTm.getAddress().toLowerCase().indexOf(searchKeyWord) > -1) {
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<StudentTm>sortedList=new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tblStudent.comparatorProperty());
        tblStudent.setItems(sortedList);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (ValidationUtil.validationList(patternList,textFieldList)){
            updateStudent();
        }
    }

    private void updateStudent() {
        String gender=rBtnFemale.isSelected() ? "Female" : "Male";
        StudentDTO studentDTO=new StudentDTO(
                txtStudentId.getText(),
                new Cust_name(txtF_name.getText(),txtL_name.getText()),
                txtAddress.getText(),
                txtPhone.getText(),
                txtDob.getText(),
                gender);

        try {
            boolean isUpdate = studentService.update(studentDTO);
            if(isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Student Updated").show();
                loadDataToTable();
                txtStudentId.setEditable(true);
            }else {
                new Alert(Alert.AlertType.ERROR,"Student Not Updated !!!").show();
            }
        }catch (NotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        Optional<ButtonType> buttonType = new Alert(
                Alert.AlertType.CONFIRMATION, "Are you suer do you want ti delete student?",
                ButtonType.YES, ButtonType.NO).showAndWait();

        if(buttonType.get()==ButtonType.YES){
            deleteStudent();
        }

    }

    private void deleteStudent() {
        try {
            boolean isDelete = studentService.delete(txtStudentId.getText());
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Student Deleted").show();
                loadDataToTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Student Not Delete !!!").show();
            }
        }catch (NotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        btnAdd.setDisable(false);
        btnReset.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        txtStudentId.setEditable(true);
        tblStudent.getSelectionModel().clearSelection();
    }

    public void btnSelectOnAction(ActionEvent actionEvent) {
        btnReset.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnAdd.setDisable(true);

        StudentTm studentTm = (StudentTm) tblStudent.getSelectionModel().getSelectedItem();
        this.olID=studentTm.getSId();
        txtStudentId.setText(studentTm.getSId());
        txtF_name.setText(studentTm.getF_name());
        txtL_name.setText(studentTm.getL_name());
        txtDob.setText(studentTm.getDob());
        txtAddress.setText(studentTm.getAddress());
        txtPhone.setText(studentTm.getPhoneNum());
        String gender = studentTm.getGender();
        txtGender.setText(studentTm.getGender());

        txtStudentId.setEditable(false);

    }

    public void btnAddOnAction(ActionEvent actionEvent) {

        if (ValidationUtil.validationList(patternList,textFieldList)){
            saveStudent();
        }
    }

    private void saveStudent() {
//        String gender=rBtnFemale.isSelected() ? "Female" : "Male";
        String gender=txtGender.getText();
        StudentDTO studentDTO=new StudentDTO(
                txtStudentId.getText(),
                new Cust_name(txtF_name.getText(),txtL_name.getText()),
                txtAddress.getText(),
                txtPhone.getText(),
                txtDob.getText(),
                gender);
        try {
            boolean isSaved = studentService.saveStudent(studentDTO);

            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Student Saved!").show();

                loadDataToTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Student not Saved!").show();
            }

        }catch (DuplicateException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    public void setGenderOnAction(ActionEvent actionEvent) {
        txtGender.setText(rBtnFemale.isSelected() ? "Female" : "Male");
    }

    public void txtStudentIdOnAction(KeyEvent keyEvent) {
        ValidationUtil.validation(idPattern, txtStudentId);
    }

    public void txtF_nameOnAction(KeyEvent keyEvent) {
        boolean validation = ValidationUtil.validation(namePattern, txtF_name);
    }

    public void txtL_nameOnAction(KeyEvent keyEvent) {
        ValidationUtil.validation(namePattern, txtL_name);
    }

    public void txtDobOnAction(KeyEvent keyEvent) {
        boolean validation = ValidationUtil.validation(dobPattern, txtDob);
    }

    public void txtPhoneOnAction(KeyEvent keyEvent) {
        boolean validation = ValidationUtil.validation(txtTelephonePattern, txtPhone);
    }

    public void txtAddressOnAction(KeyEvent keyEvent) {
        ValidationUtil.validation(addressPattern, txtAddress);
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        for (TextField textField:textFieldList){
            AnimeUtil.addCss(AnimeTypes.CORRECT,textField);
            AnimeUtil.removeCss(AnimeTypes.WRONG,textField);
            textField.clear();
        }
        txtGender.clear();
        rBtnFemale.setSelected(false);
        rBtnMale.setSelected(false);
    }
}
