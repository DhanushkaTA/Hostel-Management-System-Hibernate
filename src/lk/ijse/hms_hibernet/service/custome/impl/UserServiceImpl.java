package lk.ijse.hms_hibernet.service.custome.impl;

import lk.ijse.hms_hibernet.dao.DaoFactory;
import lk.ijse.hms_hibernet.dao.DaoTypes;
import lk.ijse.hms_hibernet.dao.custome.UserDAO;
import lk.ijse.hms_hibernet.db.FactoryConfiguration;
import lk.ijse.hms_hibernet.dto.UserDTO;
import lk.ijse.hms_hibernet.entity.User;
import lk.ijse.hms_hibernet.service.custome.UserService;
import lk.ijse.hms_hibernet.service.exception.DuplicateException;
import lk.ijse.hms_hibernet.service.exception.NotFoundException;
import lk.ijse.hms_hibernet.service.util.Convertor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static final UserDAO userDao=
            (UserDAO) DaoFactory.getDaoFactory().getDAO(DaoTypes.USER);

    @Override
    public boolean saveUser(UserDTO userDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        if(userDao.isExist(userDTO.getUid(),session)){
            throw new DuplicateException("Customer already saved Or This ID number already used");
        }


        boolean save=false;
        try {
            save = userDao.save(Convertor.toUser(userDTO), session);
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
    public UserDTO getUserDetails(String username) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            boolean isExist = userDao.isExist(username, session);
            if(isExist){
                User user = userDao.getUserFromUsername(username, session);
                return Convertor.toUserDTO(user);
            }else {
                return null;
            }

        }finally {
            session.close();
        }
    }

    @Override
    public boolean updateUser(UserDTO userDTO) throws NotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
//            if(!studentDAO.isExist(studentDTO.getSId(),session)){
//                throw new NotFoundException("Student not found! Please check details again");
//            }
            boolean isUpdate = userDao.update(Convertor.toUser(userDTO), session);
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
    public List<UserDTO> getUserList() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return userDao.getAll(session).
                    stream().map(user -> Convertor.toUserDTO(user)).collect(Collectors.toList());
        }finally {
            session.close();
        }
    }
}
