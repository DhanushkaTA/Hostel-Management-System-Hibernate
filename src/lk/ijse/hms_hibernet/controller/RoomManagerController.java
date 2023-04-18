package lk.ijse.hms_hibernet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.hms_hibernet.dto.RoomDTO;
import lk.ijse.hms_hibernet.service.ServiceFactory;
import lk.ijse.hms_hibernet.service.ServiceTypes;
import lk.ijse.hms_hibernet.service.custome.RoomService;
import lk.ijse.hms_hibernet.service.exception.DuplicateException;
import lk.ijse.hms_hibernet.service.exception.NotFoundException;
import lk.ijse.hms_hibernet.tm.RoomTm;
import lk.ijse.hms_hibernet.util.AnimeTypes;
import lk.ijse.hms_hibernet.util.AnimeUtil;
import lk.ijse.hms_hibernet.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RoomManagerController {
    public AnchorPane container;
    public TableView tblRoom;
    public TableColumn clmRoomId;
    public TableColumn clmType;
    public TableColumn clmKeyMoney;
    public TableColumn clmQty;
    public TextField txtRoomId;
    public TextField txtType;
    public TextField txtKeyMoney;
    public TextField txtQty;
    public JFXButton btnAdd;
    public JFXButton btnReset;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;
    public JFXCheckBox chkBxAc;
    public JFXCheckBox chkBxFood;
    public JFXButton btnSelect;
    public JFXButton btnClear;

    private Pattern qtyPattern;
    private Pattern keyMoneyPattern;
    private Pattern roomPattern;

    public List<Pattern>patternList;
    public List<TextField>textFieldList;

    private static final RoomService roomService=
            (RoomService) ServiceFactory.getServiceFactory().getService(ServiceTypes.ROOM);

    public void initialize(){
        btnReset.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        clmRoomId.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        clmType.setCellValueFactory(new PropertyValueFactory<>("type"));
        clmKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadDataToTable();
        regex();
        patternList=new ArrayList<>();
        patternList.add(roomPattern);
        patternList.add(keyMoneyPattern);
        patternList.add(qtyPattern);

        textFieldList=new ArrayList<>();
        textFieldList.add(txtRoomId);
        textFieldList.add(txtKeyMoney);
        textFieldList.add(txtQty);
    }

    public void regex() {
        keyMoneyPattern = Pattern.compile("^[0-9]{4,}$");
        qtyPattern=Pattern.compile("^[0-9]{1,}$");
        roomPattern=Pattern.compile("[(R)(M)]{2,2}-[0-9]{4,4}$");
    }

    private void loadDataToTable() {
//        List<RoomDTO> roomList = roomService.getRoomList();
        List<RoomTm> collect = roomService.getRoomList().
                stream().map(roomDTO ->
                        new RoomTm(roomDTO.getRoom_type_id(), roomDTO.getType(),
                                roomDTO.getKey_money(), roomDTO.getQty())).collect(Collectors.toList());

        ObservableList<RoomTm> obList=
                FXCollections.observableArrayList(collect);
        tblRoom.setItems(obList);

    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        chkBxOnAction(actionEvent);

        if (ValidationUtil.validationList(patternList,textFieldList)){
            saveRoom();
        }
    }

    private void saveRoom() {
        String s=chkBxFood.isSelected()&&chkBxAc.isSelected() ? "A/C & Food":
                chkBxFood.isSelected() ? "Food":chkBxAc.isSelected() ? "A/C" : "non";
        RoomDTO roomDTO=new RoomDTO(
                txtRoomId.getText(),
                s,
                txtKeyMoney.getText(),
                Integer.parseInt(txtQty.getText()));

        try {
            boolean isSaved = roomService.saveRoom(roomDTO);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Room Saved!").show();

                loadDataToTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Room not Saved!").show();
            }
        }catch (DuplicateException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        btnAdd.setDisable(false);
        btnReset.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        checkBox(false,false);
        tblRoom.getSelectionModel().clearSelection();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Optional<ButtonType> buttonType = new Alert(
                Alert.AlertType.CONFIRMATION, "Are you suer!! Do you want ti delete Room?",
                ButtonType.YES, ButtonType.NO).showAndWait();

        if(buttonType.get()==ButtonType.YES){
            deleteRoom();
        }
    }

    private void deleteRoom() {
        try {
            boolean isDelete = roomService.delete(txtRoomId.getText());
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Room Deleted").show();
                loadDataToTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Room Not Delete !!!").show();
            }
        }catch (NotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        chkBxOnAction(actionEvent);

        if (ValidationUtil.validationList(patternList,textFieldList)){
            updateRoom();
        }
    }

    private void updateRoom() {
        String s=chkBxFood.isSelected()&&chkBxAc.isSelected() ? "A/C & Food":
                chkBxFood.isSelected() ? "Food":chkBxAc.isSelected() ? "A/C" : "non";
        RoomDTO roomDTO=new RoomDTO(
                txtRoomId.getText(),
                s,
                txtKeyMoney.getText(),
                Integer.parseInt(txtQty.getText()));

        try {
            boolean isUpdate = roomService.update(roomDTO);
            if(isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Room details Updated").show();
                loadDataToTable();
            }else {
                new Alert(Alert.AlertType.ERROR,"Room details Not Updated !!!").show();
            }
        }catch (NotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void chkBxOnAction(ActionEvent actionEvent) {
        String s=chkBxFood.isSelected()&&chkBxAc.isSelected() ? "A/C & Food":
                chkBxFood.isSelected() ? "Food":chkBxAc.isSelected() ? "A/C" : "non";
    }

    public void btnSelectOnAction(ActionEvent actionEvent) {
        btnReset.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnAdd.setDisable(true);

        RoomTm roomTm = (RoomTm) tblRoom.getSelectionModel().getSelectedItem();

        txtRoomId.setText(roomTm.getRoom_type_id());
        txtKeyMoney.setText(roomTm.getKey_money());
        txtQty.setText(String.valueOf(roomTm.getQty()));

        switch (roomTm.getType()){
            case "A/C & Food":
                checkBox(true,true);
                break;
            case "non":
                checkBox(false,false);
                break;
            case "A/C":
                checkBox(true,false);
                break;
            case "Food":
                checkBox(false,true);
                break;
        }
    }

    private void checkBox(boolean op_ac,boolean op_food){
        chkBxAc.setSelected(op_ac);
        chkBxFood.setSelected(op_food);
    }

    public void txtKeyMoneyOnAction(KeyEvent keyEvent) {
        ValidationUtil.validation(keyMoneyPattern, txtKeyMoney);
    }

    public void txtQtyOnAction(KeyEvent keyEvent) {
       ValidationUtil.validation(qtyPattern, txtQty);
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        for (TextField textField:textFieldList){
            AnimeUtil.addCss(AnimeTypes.CORRECT,textField);
            AnimeUtil.removeCss(AnimeTypes.WRONG,textField);
            textField.clear();
        }
        chkBxFood.setSelected(false);
        chkBxAc.setSelected(false);
    }
}
