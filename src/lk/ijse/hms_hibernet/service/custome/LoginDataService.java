package lk.ijse.hms_hibernet.service.custome;

import lk.ijse.hms_hibernet.dto.LoginDataDTO;
import lk.ijse.hms_hibernet.entity.Login_Data;
import lk.ijse.hms_hibernet.service.SuperService;
import org.hibernate.Session;

public interface LoginDataService extends SuperService {
    public LoginDataDTO getLastLoginDetails();

    String getNextLoginId();

    boolean saveLoginDetails(LoginDataDTO loginDataDTO);

    boolean updateLoginDetails(LoginDataDTO loginDataDTO);
}
