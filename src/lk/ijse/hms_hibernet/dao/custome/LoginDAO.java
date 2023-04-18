package lk.ijse.hms_hibernet.dao.custome;

import lk.ijse.hms_hibernet.dao.CrudDAO;
import lk.ijse.hms_hibernet.entity.Login_Data;
import org.hibernate.Session;

public interface LoginDAO extends CrudDAO<Login_Data,String> {
    Login_Data getLastLoginDetails(Session session);

    String getNextLoginID(Session session);
}
