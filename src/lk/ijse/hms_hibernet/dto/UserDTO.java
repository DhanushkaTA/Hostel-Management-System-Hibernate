package lk.ijse.hms_hibernet.dto;

import lk.ijse.hms_hibernet.embeded.Cust_name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class UserDTO {
    private String uid;

    private Cust_name user_name;

    private String username;

    private String password;

    private String phone_number;
}
