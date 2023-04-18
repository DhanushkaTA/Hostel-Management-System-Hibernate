package lk.ijse.hms_hibernet.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StudentTm {

    private String sId;

    private String F_name;

    private String L_name;

    private String address;

    private String phoneNum;

    private String dob;

    private String gender;
}
