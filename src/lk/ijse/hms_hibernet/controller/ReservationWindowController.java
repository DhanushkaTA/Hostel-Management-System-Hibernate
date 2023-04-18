package lk.ijse.hms_hibernet.controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.hms_hibernet.dto.ReservationDTO;
import lk.ijse.hms_hibernet.dto.RoomDTO;
import lk.ijse.hms_hibernet.dto.StudentDTO;
import lk.ijse.hms_hibernet.service.ServiceFactory;
import lk.ijse.hms_hibernet.service.ServiceTypes;
import lk.ijse.hms_hibernet.service.custome.ReservationService;
import lk.ijse.hms_hibernet.service.custome.RoomService;
import lk.ijse.hms_hibernet.service.custome.StudentService;
import lk.ijse.hms_hibernet.tm.StudentTm;
import lk.ijse.hms_hibernet.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationWindowController {
    public AnchorPane context;
    public TextField txtFirstPayment;
    public ComboBox<String> cmbStudentID;
    public Text lblStudentName;
    public Text lblRes_Id;
    public Text lblDate;
    public ComboBox<String> cmbRoomId;
    public Label lblRoomId;
    public Label lblRoomType;
    public Label lblRoomQty;
    public Label lblAvi_RoomQty;
    public Label lblKeyMoney;
    public JFXCheckBox chkBoxFull;
    public JFXCheckBox chkBoxAd;
    public TextField txtRestPayment;
    public JFXButton btnBookRoom;
    public JFXButton btnCancel;

    private static final StudentService studentService=
            (StudentService) ServiceFactory.getServiceFactory().getService(ServiceTypes.STUDENT);

    private static final RoomService roomService=
            (RoomService) ServiceFactory.getServiceFactory().getService(ServiceTypes.ROOM);

    private static final ReservationService reservationService=
            (ReservationService) ServiceFactory.getServiceFactory().getService(ServiceTypes.RESERVATION);

    private static StudentDTO studentDTO;
    private static RoomDTO roomDTO;

    public void initialize(){
        loadStudentIds();
        loadRoomIds();

        lblDate.setText(TimeUtil.getDate());
        lblStudentName.setText("");

        setNextReservationId();
    }

    private void setNextReservationId() {
        String nextOrderID = reservationService.getNextOrderID();
        lblRes_Id.setText(nextOrderID);
    }

    private void loadRoomIds() {
        List<String> roomIds = roomService.getRoomList().
                stream().map(roomDTO -> roomDTO.getRoom_type_id()).collect(Collectors.toList());
        cmbRoomId.setItems(FXCollections.observableArrayList(roomIds));
    }

    private void loadStudentIds() {
//        List<StudentDTO> studentList = studentService.getStudentList();
//        List<String>idList=new ArrayList<>();
//        for (StudentDTO studentDTO:studentList){
//            idList.add(studentDTO.getSId());
//        }
//        ObservableList<String> obList=
//                FXCollections.observableArrayList(idList);
//        cmbStudentID.setItems(obList);

        List<String> studentIds = studentService.getStudentList().
                stream().map(studentDTO -> studentDTO.getSId()).collect(Collectors.toList());
        cmbStudentID.setItems(FXCollections.observableArrayList(studentIds));
    }

    public void txtFirstPaymen(KeyEvent keyEvent) {
        int i = Integer.parseInt(txtFirstPayment.getText());
        int rest = Integer.parseInt(lblKeyMoney.getText()) - i;
        txtRestPayment.setText("Less "+rest);
    }

    public void cmbStudentIDOnAction(ActionEvent actionEvent) {
        studentDTO = studentService.getStudent(cmbStudentID.getSelectionModel().getSelectedItem());
        lblStudentName.setText(studentDTO.getName().getF_name()+" "+studentDTO.getName().getL_name());
    }

    public void cmbRoomIdOnAction(ActionEvent actionEvent) {
        roomDTO = roomService.getRoom(cmbRoomId.getSelectionModel().getSelectedItem());
        lblRoomId.setText(roomDTO.getRoom_type_id());
        lblRoomQty.setText(String.valueOf(roomDTO.getQty()));
        lblKeyMoney.setText(roomDTO.getKey_money());
        lblRoomType.setText(roomDTO.getType());
        //set available room qty
        int availableRoomQty = roomService.getAvailableRoomQty(cmbRoomId.getSelectionModel().getSelectedItem());
        lblAvi_RoomQty.setText(String.valueOf(availableRoomQty));

    }

    public void chkBoxFullOnAction(ActionEvent actionEvent) {
        chkBoxAd.setSelected(false);
        txtFirstPayment.setText(lblKeyMoney.getText());
        txtFirstPayment.setEditable(false);
        txtRestPayment.clear();
    }

    public void chkBoxAdOnAction(ActionEvent actionEvent) {
        chkBoxFull.setSelected(false);
        txtFirstPayment.setEditable(true);
    }

    public void btnBookRoomOnAction(ActionEvent actionEvent) {

        if(!chkBoxFull.isSelected() && !chkBoxAd.isSelected()){

        }else {
            addReservation();
        }
    }

    private void addReservation() {
        String status=chkBoxFull.isSelected() ? "Full paid" : "Less "+txtRestPayment.getText();
        ReservationDTO reservationDTO=new ReservationDTO(lblRes_Id.getText(),lblDate.getText(),status,studentDTO,roomDTO);
        boolean isSaved = reservationService.save(reservationDTO);

        if(isSaved){
            new Alert(Alert.AlertType.INFORMATION,"Reservation Recoded").show();
            setNextReservationId();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Reservation not Recoded").show();
        }
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {

    }

    public void txtFirstPayment(KeyEvent keyEvent) {
        try {
            if(txtFirstPayment.getText().isEmpty()){
                txtRestPayment.setText(lblKeyMoney.getText());
            }else {
                double i = Double.parseDouble(txtFirstPayment.getText());
                double rest = Double.parseDouble(lblKeyMoney.getText()) - i;
                txtRestPayment.setText(String.valueOf(rest));
            }
        }catch (NumberFormatException e){
            new Shake(txtFirstPayment).play();
        }


    }
}
