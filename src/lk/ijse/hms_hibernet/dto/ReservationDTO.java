package lk.ijse.hms_hibernet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ReservationDTO {

    private String res_id;

    private String date;

    private String status;

    private StudentDTO studentDTO;

    private RoomDTO roomDTO;

    public ReservationDTO(String res_id, String date, String status, StudentDTO studentDTO) {
        this.res_id = res_id;
        this.date = date;
        this.status = status;
        this.studentDTO = studentDTO;
    }
}
