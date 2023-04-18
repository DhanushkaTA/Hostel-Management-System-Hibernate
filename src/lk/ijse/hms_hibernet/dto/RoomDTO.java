package lk.ijse.hms_hibernet.dto;

import lk.ijse.hms_hibernet.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RoomDTO {

    private String room_type_id;

    private String type;

    private String key_money;

    private int qty;

    private List<ReservationDTO> reservationList;

    public RoomDTO(String room_type_id, String type, String key_money, int qty) {
        this.room_type_id = room_type_id;
        this.type = type;
        this.key_money = key_money;
        this.qty = qty;
    }
}
