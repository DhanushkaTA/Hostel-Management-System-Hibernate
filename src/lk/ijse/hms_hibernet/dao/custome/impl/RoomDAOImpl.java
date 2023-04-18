package lk.ijse.hms_hibernet.dao.custome.impl;

import lk.ijse.hms_hibernet.dao.custome.RoomDAO;
import lk.ijse.hms_hibernet.dao.exception.ConstraintViolationException;
import lk.ijse.hms_hibernet.entity.Room;
import lk.ijse.hms_hibernet.entity.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public boolean save(Room room, Session session) throws ConstraintViolationException {
        try {
            session.save(room);
            return true;
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    public boolean update(Room room, Session session) throws ConstraintViolationException{
        try {
            session.update(room.getRoom_type_id(),room);
            return true;
        }catch (Exception e){
            throw new ConstraintViolationException(e);
//            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String pk, Session session) {
        try {
            session.delete(load(pk,session));
            return true;
        }catch (Exception e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public List<Room> getAll(Session session) {
        String hql="from Room ";
        Query query = session.createQuery(hql);
        List<Room> list = query.list();
        return list;
    }

    @Override
    public Room get(String pk, Session session) {
        return session.get(Room.class, pk);
    }

    @Override
    public Room load(String pk, Session session) {
        return session.load(Room.class, pk);
    }

    @Override
    public boolean isExist(String rId,Session session) {
        Room room = session.get(Room.class, rId);
        System.out.println("Room : "+room);
        if(room==null){
            return false;
        }
        return true;
    }
}
