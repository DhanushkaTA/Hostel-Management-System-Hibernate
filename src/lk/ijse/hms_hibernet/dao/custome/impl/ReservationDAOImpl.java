package lk.ijse.hms_hibernet.dao.custome.impl;

import lk.ijse.hms_hibernet.dao.custome.ReservationDAO;
import lk.ijse.hms_hibernet.dao.exception.ConstraintViolationException;
import lk.ijse.hms_hibernet.entity.Reservation;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public boolean save(Reservation reservation, Session session) throws ConstraintViolationException {
        boolean save1=false;
        try {
            String save = (String)session.save(reservation);
            if(save.equals(reservation.getRes_id())){
                save1=true;
            }
            return save1;
        }catch (Exception e){
//            throw new RuntimeException(e);
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean update(Reservation reservation, Session session) {
        try {
            session.update(reservation.getRes_id(),reservation);
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
    public List<Reservation> getAll(Session session) {
        String hql="from Reservation ";
        Query query = session.createQuery(hql);
        List<Reservation> list = query.list();
        return list;
    }

    @Override
    public Reservation get(String pk, Session session) {
        return session.get(Reservation.class, pk);
    }

    @Override
    public Reservation load(String pk, Session session) {
        return session.load(Reservation.class, pk);
    }

    @Override
    public boolean isExist(String pk,Session session) {
        return false;
    }

    public String getNextResID(Session session) {

        try {
            String sql="SELECT * from Reservation ORDER BY res_id DESC LIMIT 1";
            NativeQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.addEntity(Reservation.class);
            List<Reservation> list = sqlQuery.list();
            System.out.println("List : "+list);

            String lastResID="";

            if (!list.isEmpty()){
                System.out.println(list.get(0));
                lastResID=list.get(0).getRes_id();
            }
            String nextOrderId=generateNextResId(lastResID);
            return nextOrderId;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String generateNextResId(String lastResID) {
        String date="";
        String newDate="";
        date=new SimpleDateFormat("yyyy/MM").format(new Date());
        newDate="Res/"+date;

        if((lastResID.equals(""))==false) {
            String[] ids = lastResID.split("@");
            int id = Integer.parseInt(ids[1]);
            id += 1;


            boolean isEquals=isDateEquals(ids[0],newDate);
            if(!isEquals){
                ids[0]=newDate;
                id=1;
            }

            String newResId=String.format("@%04d",id);
            return ids[0] + newResId;
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
