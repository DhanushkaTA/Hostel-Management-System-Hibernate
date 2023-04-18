package lk.ijse.hms_hibernet.dao.custome;

import lk.ijse.hms_hibernet.dao.CrudDAO;
import lk.ijse.hms_hibernet.entity.User;
import org.hibernate.Session;

public interface UserDAO extends CrudDAO<User,String> {
    User getUserFromUsername(String username, Session session);
}
