package lk.ijse.hms_hibernet.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hms_hibernet.dto.CustomDTO;
import lk.ijse.hms_hibernet.service.ServiceFactory;
import lk.ijse.hms_hibernet.service.ServiceTypes;
import lk.ijse.hms_hibernet.service.custome.StudentService;
import lk.ijse.hms_hibernet.tm.RemainingTm;

import java.util.ArrayList;
import java.util.List;

public class ResStuWindowController {
    public AnchorPane context;
    public TableView tblStudent;
    public TableColumn clmStudentId;
    public TableColumn clmName;
    public TableColumn clmPhoneNum;
    public TableColumn clmReservation;
    public TableColumn clmRoomID;
    public TableColumn clmKeyMoney;
    public TableColumn clmRemainingAmount;


    private static final StudentService studentService=
            (StudentService) ServiceFactory.getServiceFactory().getService(ServiceTypes.STUDENT);

    public void initialize(){

        clmStudentId.setCellValueFactory(new PropertyValueFactory<>("sId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        clmReservation.setCellValueFactory(new PropertyValueFactory<>("res_id"));
        clmRoomID.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        clmRemainingAmount.setCellValueFactory(new PropertyValueFactory<>("status"));
        clmKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));

        loadDataToTable();
    }

    private void loadDataToTable() {
        List<CustomDTO> remainingStudentList = studentService.getRemainingStudentList();
        List<RemainingTm>remainingTmList=new ArrayList<>();
        for (CustomDTO customDTO:remainingStudentList){
            remainingTmList.add(
                    new RemainingTm(
                            customDTO.getSId(),
                            customDTO.getName().getF_name()+" "+customDTO.getName().getL_name(),
                            customDTO.getPhoneNum(),
                            customDTO.getRes_id(),
                            customDTO.getRoom_type_id(),
                            customDTO.getStatus(),
                            customDTO.getKey_money())
            );
        }
        tblStudent.setItems(FXCollections.observableArrayList(remainingTmList));
    }
}
