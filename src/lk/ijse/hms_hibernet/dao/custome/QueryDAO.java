package lk.ijse.hms_hibernet.dao.custome;

import lk.ijse.hms_hibernet.dao.SuperDAO;
import lk.ijse.hms_hibernet.entity.CustomEntity;
import org.hibernate.Session;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    public List<CustomEntity> getRemainingStudentList(Session session);
    public List<CustomEntity> getStudentListWhoReservedRoom(Session session);
}
