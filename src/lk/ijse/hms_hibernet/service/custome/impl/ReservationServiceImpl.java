package lk.ijse.hms_hibernet.service.custome.impl;

import lk.ijse.hms_hibernet.dao.DaoFactory;
import lk.ijse.hms_hibernet.dao.DaoTypes;
import lk.ijse.hms_hibernet.dao.custome.ReservationDAO;
import lk.ijse.hms_hibernet.db.FactoryConfiguration;
import lk.ijse.hms_hibernet.dto.ReservationDTO;
import lk.ijse.hms_hibernet.service.custome.ReservationService;
import lk.ijse.hms_hibernet.service.exception.DuplicateException;
import lk.ijse.hms_hibernet.service.util.Convertor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ReservationServiceImpl implements ReservationService {
    private static final ReservationDAO reservationDao=
            (ReservationDAO) DaoFactory.getDaoFactory().getDAO(DaoTypes.RESERVATION);

    @Override
    public String getNextOrderID() {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            return reservationDao.getNextResID(session);
        }finally {
            session.close();
        }
    }

    @Override
    public boolean save(ReservationDTO reservationDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

//        if(roomDao.isExist(roomDTO.getRoom_type_id(),session)){
//            throw new DuplicateException("Room already saved Or This ID number already used");
//        }

        boolean save=false;
        try {
            save = reservationDao.save(Convertor.toReservation(reservationDTO), session);
            transaction.commit();
            return save;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }
}
