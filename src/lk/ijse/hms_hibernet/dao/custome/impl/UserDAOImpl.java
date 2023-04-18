package lk.ijse.hms_hibernet.dao.custome.impl;

import lk.ijse.hms_hibernet.dao.custome.UserDAO;
import lk.ijse.hms_hibernet.dao.exception.ConstraintViolationException;
import lk.ijse.hms_hibernet.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean save(User user, Session session) throws ConstraintViolationException {
        boolean save1=false;
        try {
            String save = (String)session.save(user);
            if(save.equals(user.getUid())){
                save1=true;
            }
            return save1;
        }catch (Exception e){
//            throw new RuntimeException(e);
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean update(User user, Session session) {
        try {
            session.update(user.getUid(),user);
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
    public List<User> getAll(Session session) {
        String hql="from User ";
        Query query = session.createQuery(hql);
        List<User> list = query.list();
        return list;
    }

    @Override
    public User get(String pk, Session session) {
        return session.get(User.class,pk);
    }

    @Override
    public User load(String pk, Session session) {
        return session.load(User.class, pk);
    }

    @Override
    public boolean isExist(String username,Session session) {
        User user = this.getUserFromUsername(username,session);
        System.out.println("User : "+user);
        if(user==null){
            return false;
        }
        return true;
    }

    @Override
    public User getUserFromUsername(String username,Session session) {
        String hql="FROM User WHERE username=:user_name";
        Query query = session.createQuery(hql);
        query.setParameter("user_name",username);
        List<User> list = query.list();
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
