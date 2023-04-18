package lk.ijse.hms_hibernet.service.util;

import lk.ijse.hms_hibernet.dto.*;
import lk.ijse.hms_hibernet.entity.*;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Convertor {

    public static Student toStudent(StudentDTO studentDTO){
        return new Student(
                studentDTO.getSId(),
                studentDTO.getName(),
                studentDTO.getAddress(),
                studentDTO.getPhoneNum(),
                studentDTO.getDob(),
                studentDTO.getGender());
    }

    public static StudentDTO toStudentDTO(Student student){
        return new StudentDTO(
                student.getSId(),
                student.getName(),
                student.getAddress(),
                student.getPhoneNum(),
                student.getDob(),
                student.getGender());
    }

    public static Room toRoom(RoomDTO roomDTO){
        return new Room(
                roomDTO.getRoom_type_id(),
                roomDTO.getType(),
                roomDTO.getKey_money(),
                roomDTO.getQty() );
    }

    public static RoomDTO toRoomDTO(Room room){
        return new RoomDTO(
                room.getRoom_type_id(),
                room.getType(),
                room.getKey_money(),
                room.getQty());
    }

    public static Reservation toReservation(ReservationDTO reservationDTO){
        return new Reservation(
                reservationDTO.getRes_id(),
                reservationDTO.getDate(),
                reservationDTO.getStatus(),
                Convertor.toStudent(reservationDTO.getStudentDTO()),
                Convertor.toRoom(reservationDTO.getRoomDTO()));
    }

    public static ReservationDTO toReservationDTO(Reservation reservation){
        return new ReservationDTO(
                reservation.getRes_id(),
                reservation.getDate(),
                reservation.getStatus(),
                Convertor.toStudentDTO(reservation.getStudent()),
                Convertor.toRoomDTO(reservation.getRoom()));
    }

    @NonNull
    private static List<ReservationDTO> toConvertReservationDtoForRoom(List<Reservation> reservationList) {
        List<ReservationDTO>list=new ArrayList<>();
        for (Reservation reservation: reservationList) {
            list.add(Convertor.toReservationDTO(reservation));
        }
        return list;
    }

    private static ReservationDTO toReservationDTOForConvertor(Reservation reservation){
        return new ReservationDTO(
                reservation.getRes_id(),
                reservation.getDate(),
                reservation.getStatus(),
                Convertor.toStudentDTO(reservation.getStudent()));
    }

    public static CustomEntity toCustomEntity(CustomDTO customDTO){
        return new CustomEntity(customDTO.getSId(),
                customDTO.getName(),
                customDTO.getPhoneNum(),
                customDTO.getRes_id(),
                customDTO.getDate(),
                customDTO.getStatus(),
                customDTO.getRoom_type_id(),
                customDTO.getKey_money());
    }

    public static CustomDTO toCustomDTO(CustomEntity customEntity){
        return new CustomDTO(customEntity.getSId(),
                customEntity.getName(),
                customEntity.getPhoneNum(),
                customEntity.getRes_id(),
                customEntity.getDate(),
                customEntity.getStatus(),
                customEntity.getRoom_type_id(),
                customEntity.getKey_money());
    }

    public static CustomEntity toCustomStudentEntity(CustomDTO customDTO){
        return new CustomEntity(
                customDTO.getSId(),
                customDTO.getName(),
                customDTO.getAddress(),
                customDTO.getPhoneNum(),
                customDTO.getDob(),
                customDTO.getGender());
    }

    public static CustomDTO toCustomStudentDTO(CustomEntity custom){
        return new CustomDTO(
                custom.getSId(),
                custom.getName(),
                custom.getAddress(),
                custom.getPhoneNum(),
                custom.getDob(),
                custom.getGender());
    }

    public static User toUser(UserDTO userDTO){
        return new User(
                userDTO.getUid(),
                userDTO.getUser_name(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getPhone_number() );
    }

    public static UserDTO toUserDTO(User user){
        return new UserDTO(
                user.getUid(),
                user.getUser_name(),
                user.getUsername(),
                user.getPassword(),
                user.getPhone_number());
    }

    public static Login_Data toLoginData(LoginDataDTO loginDataDTO){
        return new Login_Data(
                loginDataDTO.getLoginId(),
                loginDataDTO.getLogInDate(),
                loginDataDTO.getLoginTime(),
                loginDataDTO.getLogOutDate(),
                loginDataDTO.getLogOutTime(),
                loginDataDTO.getStatus(),
                Convertor.toUser(loginDataDTO.getUserDTO()));
    }

    public static LoginDataDTO toLoginDataDTO(Login_Data login_Data){
        return new LoginDataDTO(
                login_Data.getLoginId(),
                login_Data.getLogInDate(),
                login_Data.getLoginTime(),
                login_Data.getLogOutDate(),
                login_Data.getLogOutTime(),
                login_Data.getStatus(),
                Convertor.toUserDTO(login_Data.getUser()));
    }
}
