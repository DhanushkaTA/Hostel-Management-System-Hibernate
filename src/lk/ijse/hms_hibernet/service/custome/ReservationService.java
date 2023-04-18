package lk.ijse.hms_hibernet.service.custome;

import lk.ijse.hms_hibernet.dto.ReservationDTO;
import lk.ijse.hms_hibernet.service.SuperService;
import org.hibernate.Session;

public interface ReservationService extends SuperService {
    public String getNextOrderID();

    public boolean save(ReservationDTO reservationDTO);
}
