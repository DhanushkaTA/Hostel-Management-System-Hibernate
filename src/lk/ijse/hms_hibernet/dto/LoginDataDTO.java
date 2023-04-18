package lk.ijse.hms_hibernet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LoginDataDTO {
    private String loginId;

    private String logInDate;

    private String loginTime;

    private String logOutDate;

    private String logOutTime;

    private String status;

    private UserDTO userDTO;

}
