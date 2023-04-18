package lk.ijse.hms_hibernet.dao.custome.impl;

import lk.ijse.hms_hibernet.dao.custome.LoginDAO;
import lk.ijse.hms_hibernet.dao.exception.ConstraintViolationException;
import lk.ijse.hms_hibernet.entity.Login_Data;
import lk.ijse.hms_hibernet.entity.Reservation;
import lk.ijse.hms_hibernet.entity.User;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public boolean save(Login_Data loginData, Session session) throws ConstraintViolationException {
        boolean save1=false;
        try {
            String save = (String)session.save(loginData);
            if(save.equals(loginData.getLoginId())){
                save1=true;
            }
            return save1;
        }catch (Exception e){
//            throw new RuntimeException(e);
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean update(Login_Data loginData, Session session) {
        try {
            session.update(loginData.getLoginId(),loginData);
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
    public List<Login_Data> getAll(Session session) {
        String hql="from Login_Data ";
        Query query = session.createQuery(hql);
        List<Login_Data> list = query.list();
        return list;
    }

    @Override
    public Login_Data get(String pk, Session session) {
        return session.get(Login_Data.class, pk);
    }

    @Override
    public Login_Data load(String pk, Session session) {
        return session.load(Login_Data.class, pk);
    }

    @Override
    public boolean isExist(String lId,Session session) {
        Login_Data login_data = this.get(lId, session);
        System.out.println("Login Data : "+login_data);
        if(login_data==null){
            return false;
        }
        return true;
    }

    @Override
    public Login_Data getLastLoginDetails(Session session) {
        String sql="SELECT * from Login_Data ORDER BY loginId DESC LIMIT 1";
        NativeQuery query = session.createNativeQuery(sql);
        query.addEntity(Login_Data.class);
        List<Login_Data> list = query.list();
        System.out.println(list.size());

        if(list.size()>0){
            Login_Data login_data = list.get(0);
            System.out.println(login_data);
            return login_data;
        }
        return null;
    }

    public String getNextLoginID(Session session) {

        try {
            String sql="SELECT * from Login_Data ORDER BY loginId DESC LIMIT 1";
            NativeQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.addEntity(Login_Data.class);
            List<Login_Data> list = sqlQuery.list();
            System.out.println("List : "+list);

            String lastLoginID="";

            if (!list.isEmpty()){
                System.out.println(list.get(0));
                lastLoginID=list.get(0).getLoginId();
            }
            String nextOrderId=generateNextLoginId(lastLoginID);
            return nextOrderId;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String generateNextLoginId(String lastLoginID) {
        String date="";
        String newDate="";
        date=new SimpleDateFormat("yyyy/MM").format(new Date());
        newDate="L/"+date;

        if((lastLoginID.equals(""))==false) {
            String[] ids = lastLoginID.split("@");
            int id = Integer.parseInt(ids[1]);
            id += 1;


            boolean isEquals=isDateEquals(ids[0],newDate);
            if(!isEquals){
                ids[0]=newDate;
                id=1;
            }

            String newLoginId=String.format("@%04d",id);
            return ids[0] + newLoginId;
        }

        return newDate+"@0001";
    }

    private boolean isDateEquals(String id, String date) {
        if(id.equals(date)){
            return true;
        }
        return false;
    }
}
