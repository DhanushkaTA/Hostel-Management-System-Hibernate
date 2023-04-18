package lk.ijse.hms_hibernet.entity;

import lk.ijse.hms_hibernet.embeded.Cust_name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CustomEntity {
    private String sId;

    private Cust_name name;

    private String address;

    private String phoneNum;

    private String dob;

    private String gender;

    private String res_id;

    private String date;

    private String status;

    private String room_type_id;

    private String type;

    private String key_money;

    private int qty;

    public CustomEntity(String sId, Cust_name name, String phoneNum, String res_id, String date, String status, String room_type_id, String key_money) {
        this.sId = sId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.res_id = res_id;
        this.date = date;
        this.status = status;
        this.room_type_id = room_type_id;
        this.key_money = key_money;
    }

    public CustomEntity(String sId, Cust_name name, String address, String phoneNum, String dob, String gender) {
        this.sId = sId;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.dob = dob;
        this.gender = gender;
    }
}
