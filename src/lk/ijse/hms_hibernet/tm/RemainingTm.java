package lk.ijse.hms_hibernet.tm;

import lk.ijse.hms_hibernet.embeded.Cust_name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RemainingTm {
    private String sId;
    private String name;
    private String phoneNum;
    private String res_id;
    private String room_type_id;
    private String status;
    private String key_money;
}
