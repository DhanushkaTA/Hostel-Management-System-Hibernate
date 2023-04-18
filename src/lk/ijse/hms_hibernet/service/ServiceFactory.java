package lk.ijse.hms_hibernet.service;

import lk.ijse.hms_hibernet.service.custome.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory(){

    }

    public static ServiceFactory getServiceFactory(){
        return serviceFactory==null ?
                serviceFactory=new ServiceFactory() : serviceFactory;
    }

    public SuperService getService(ServiceTypes serviceTypes){
        switch (serviceTypes){
            case STUDENT:
                return new StudentServiceImpl();
            case ROOM:
                return new RoomServiceImpl();
            case RESERVATION:
                return new ReservationServiceImpl();
            case USER:
                return new UserServiceImpl();
            case LOGIN:
                return new LoginDataServiceImpl();
            default:
                return null;
        }
    }
}
