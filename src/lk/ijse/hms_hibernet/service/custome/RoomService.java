package lk.ijse.hms_hibernet.service.custome;

import lk.ijse.hms_hibernet.dto.RoomDTO;
import lk.ijse.hms_hibernet.dto.StudentDTO;
import lk.ijse.hms_hibernet.service.SuperService;
import lk.ijse.hms_hibernet.service.exception.DuplicateException;
import lk.ijse.hms_hibernet.service.exception.NotFoundException;

import java.util.List;

public interface RoomService extends SuperService {
    public boolean saveRoom(RoomDTO roomDTO) throws DuplicateException;

    public List<RoomDTO> getRoomList();

    public  boolean update(RoomDTO roomDTO) throws NotFoundException;

    public boolean delete(String rId);

    public RoomDTO getRoom(String rId);

    public int getAvailableRoomQty(String rId);
}
