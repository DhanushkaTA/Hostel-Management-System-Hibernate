package lk.ijse.hms_hibernet.dao.custome;

import lk.ijse.hms_hibernet.dao.CrudDAO;
import lk.ijse.hms_hibernet.entity.Reservation;
import org.hibernate.Session;

public interface ReservationDAO extends CrudDAO<Reservation,String> {
    public String getNextResID(Session session);
}
