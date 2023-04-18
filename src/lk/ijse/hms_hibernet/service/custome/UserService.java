package lk.ijse.hms_hibernet.service.custome;

import lk.ijse.hms_hibernet.dto.UserDTO;
import lk.ijse.hms_hibernet.service.SuperService;
import lk.ijse.hms_hibernet.service.exception.NotFoundException;

import java.util.List;

public interface UserService extends SuperService {
    public boolean saveUser(UserDTO userDTO);

    public UserDTO getUserDetails(String username);

    public boolean updateUser(UserDTO userDTO) throws NotFoundException;

    public List<UserDTO> getUserList();
}
