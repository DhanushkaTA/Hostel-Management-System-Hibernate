package lk.ijse.hms_hibernet.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RoomTm {

    private String room_type_id;

    private String type;

    private String key_money;

    private int qty;
}
