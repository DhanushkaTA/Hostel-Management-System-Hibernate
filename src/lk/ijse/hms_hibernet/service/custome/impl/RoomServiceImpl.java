package lk.ijse.hms_hibernet.service.custome.impl;

import lk.ijse.hms_hibernet.dao.DaoFactory;
import lk.ijse.hms_hibernet.dao.DaoTypes;
import lk.ijse.hms_hibernet.dao.custome.RoomDAO;
import lk.ijse.hms_hibernet.db.FactoryConfiguration;
import lk.ijse.hms_hibernet.dto.RoomDTO;
import lk.ijse.hms_hibernet.entity.Room;
import lk.ijse.hms_hibernet.service.custome.RoomService;
import lk.ijse.hms_hibernet.service.exception.DuplicateException;
import lk.ijse.hms_hibernet.service.exception.NotFoundException;
import lk.ijse.hms_hibernet.service.util.Convertor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class RoomServiceImpl implements RoomService {

    private static final RoomDAO roomDao= (RoomDAO) DaoFactory.getDaoFactory().getDAO(DaoTypes.ROOM);

    @Override
    public boolean saveRoom(RoomDTO roomDTO) throws DuplicateException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        if(roomDao.isExist(roomDTO.getRoom_type_id(),session)){
            throw new DuplicateException("Room already saved Or This ID number already used");
        }


        boolean save=false;
        try {
            save = roomDao.save(Convertor.toRoom(roomDTO), session);
            transaction.commit();
            return save;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public List<RoomDTO> getRoomList() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return roomDao.getAll(session).
                    stream().map(room -> Convertor.toRoomDTO(room)).collect(Collectors.toList());
        }finally {
            session.close();
        }
    }

    @Override
    public boolean update(RoomDTO roomDTO) throws NotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
//            if(!studentDAO.isExist(studentDTO.getSId(),session)){
//                throw new NotFoundException("Student not found! Please check details again");
//            }
            boolean isUpdate = roomDao.update(Convertor.toRoom(roomDTO), session);
            transaction.commit();
            return isUpdate;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String rId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            boolean exist = roomDao.isExist(rId, session);
            if (!exist){
                throw new NotFoundException("Room not found! Please check details again");
            }

            boolean isDelete = roomDao.delete(rId, session);
            transaction.commit();
            return isDelete;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public RoomDTO getRoom(String rId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return Convertor.toRoomDTO(roomDao.get(rId, session));
        }finally {
            session.close();
        }
    }

    @Override
    public int getAvailableRoomQty(String rId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Room room = roomDao.get(rId, session);
            return (room.getQty()-room.getReservationList().size());
        }finally {
            session.close();
        }

    }
}
