package lk.ijse.hms_hibernet.dao.custome.impl;

import lk.ijse.hms_hibernet.dao.custome.QueryDAO;
import lk.ijse.hms_hibernet.embeded.Cust_name;
import lk.ijse.hms_hibernet.entity.CustomEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {

    public List<CustomEntity> getRemainingStudentList(Session session) {
        try {
            String hql="SELECT s.sId," +
                    "s.name.f_name," +
                    "s.name.l_name,s.phoneNum," +
                    "r.res_id," +
                    "r.date," +
                    "r.status," +
                    "r.room.room_type_id," +
                    "ro.key_money " +
                    "FROM Reservation r INNER JOIN Room ro ON r.room=ro.room_type_id" +
                    " INNER JOIN  Student s ON s.sId=r.student WHERE r.status LIKE '%Less%'";
            Query query = session.createQuery(hql);
            List<Object[]> list = query.list();

            List<CustomEntity> customEntityList =new ArrayList<>();
            for (Object[] objects : list) {
                customEntityList.add(
                        new CustomEntity(
                                (String) objects[0],
                                new Cust_name((String) objects[1],(String) objects[2]),
                                (String)objects[3],
                                (String)objects[4],
                                (String)objects[5],
                                (String)objects[6],
                                (String)objects[7],
                                (String) objects[8]
                        )
                );
            }
            return customEntityList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CustomEntity> getStudentListWhoReservedRoom(Session session){
        try {
            String hql2="SELECT s.sId,s.name.f_name,s.name.l_name,s.address,s.phoneNum,s.dob,s.gender " +
                    "FROM Student s INNER JOIN Reservation r ON s.sId=r.student.sId GROUP BY s.sId";
            Query query2 = session.createQuery(hql2);
            List<Object[]> list = query2.list();

            List<CustomEntity>customEntityList=new ArrayList<>();

            for (Object[] objects:list){
                customEntityList.add(
                        new CustomEntity(
                                (String) objects[0],
                                new Cust_name((String) objects[1], (String) objects[2]),
                                (String) objects[3],
                                (String) objects[4],
                                (String) objects[5],
                                (String) objects[6]));
            }
            return customEntityList;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
