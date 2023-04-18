package lk.ijse.hms_hibernet.service.custome.impl;

import lk.ijse.hms_hibernet.dao.DaoFactory;
import lk.ijse.hms_hibernet.dao.DaoTypes;
import lk.ijse.hms_hibernet.dao.custome.LoginDAO;
import lk.ijse.hms_hibernet.db.FactoryConfiguration;
import lk.ijse.hms_hibernet.dto.LoginDataDTO;
import lk.ijse.hms_hibernet.entity.Login_Data;
import lk.ijse.hms_hibernet.service.custome.LoginDataService;
import lk.ijse.hms_hibernet.service.exception.DuplicateException;
import lk.ijse.hms_hibernet.service.util.Convertor;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LoginDataServiceImpl implements LoginDataService {
    private static final LoginDAO loginDao=
            (LoginDAO) DaoFactory.getDaoFactory().getDAO(DaoTypes.LOGIN);

    @Override
    public LoginDataDTO getLastLoginDetails() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Login_Data lastLoginDetails = loginDao.getLastLoginDetails(session);
            if (lastLoginDetails==null){
                return null;
            }
            return Convertor.toLoginDataDTO(lastLoginDetails);
        }finally {
            session.close();
        }
    }

    @Override
    public String getNextLoginId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return loginDao.getNextLoginID(session);
        }finally {
            session.close();
        }
    }

    @Override
    public boolean saveLoginDetails(LoginDataDTO loginDataDTO) throws DuplicateException{
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        if(loginDao.isExist(loginDataDTO.getLoginId(),session)){
            throw new DuplicateException("Login ID already used!");
        }


        boolean save=false;
        try {
            save = loginDao.save(Convertor.toLoginData(loginDataDTO), session);
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
    public boolean updateLoginDetails(LoginDataDTO loginDataDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
//            if(!studentDAO.isExist(studentDTO.getSId(),session)){
//                throw new NotFoundException("Student not found! Please check details again");
//            }
            boolean isUpdate = loginDao.update(Convertor.toLoginData(loginDataDTO), session);
            transaction.commit();
            return isUpdate;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }
}
