package lk.ijse.hms_hibernet.dao;

import lk.ijse.hms_hibernet.dao.custome.impl.*;
import lk.ijse.hms_hibernet.entity.User;

public class DaoFactory {

    private static DaoFactory daoFactory;

    private DaoFactory(){

    }

    public static DaoFactory getDaoFactory(){
        return daoFactory==null ? daoFactory=new DaoFactory() : daoFactory;
    }

    public SuperDAO getDAO(DaoTypes daoTypes){
        switch (daoTypes){
            case STUDENT:
                return new StudentDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case USER:
                return new UserDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            default:
                return null;
        }
    }
}
