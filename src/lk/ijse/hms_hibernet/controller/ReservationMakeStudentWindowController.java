package lk.ijse.hms_hibernet.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hms_hibernet.dto.CustomDTO;
import lk.ijse.hms_hibernet.dto.StudentDTO;
import lk.ijse.hms_hibernet.service.ServiceFactory;
import lk.ijse.hms_hibernet.service.ServiceTypes;
import lk.ijse.hms_hibernet.service.custome.StudentService;
import lk.ijse.hms_hibernet.tm.StudentTm;

import java.util.ArrayList;
import java.util.List;

public class ReservationMakeStudentWindowController {

    public AnchorPane context;
    public TableView tblStudent;
    public TableColumn clmStudentId;
    public TableColumn clmF_name;
    public TableColumn clmL_name;
    public TableColumn clmAddress;
    public TableColumn clmPhone;
    public TableColumn clmDob;
    public TableColumn clmGender;

    private static final StudentService studentService=
            (StudentService) ServiceFactory.getServiceFactory().getService(ServiceTypes.STUDENT);

    public void initialize(){
        clmStudentId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        clmF_name.setCellValueFactory(new PropertyValueFactory<>("F_name"));
        clmL_name.setCellValueFactory(new PropertyValueFactory<>("L_name"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        clmDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        clmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        loadDataToTable();
    }

    private void loadDataToTable() {
        List<CustomDTO> studentList = studentService.getStudentListWhoReservedRoom();

        List<StudentTm> list=new ArrayList<>();

        for(CustomDTO customDTO:studentList){
            list.add(
                    new StudentTm(
                            customDTO.getSId(),
                            customDTO.getName().getF_name(),
                            customDTO.getName().getL_name(),
                            customDTO.getAddress(),
                            customDTO.getPhoneNum(),
                            customDTO.getDob(),
                            customDTO.getGender()));
        }
        tblStudent.setItems(FXCollections.observableArrayList(list));
    }
}
