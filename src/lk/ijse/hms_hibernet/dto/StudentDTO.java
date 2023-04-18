package lk.ijse.hms_hibernet.dto;

import lk.ijse.hms_hibernet.embeded.Cust_name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StudentDTO {
    private String sId;

    private Cust_name name;

    private String address;

    private String phoneNum;

    private String dob;

    private String gender;
}
