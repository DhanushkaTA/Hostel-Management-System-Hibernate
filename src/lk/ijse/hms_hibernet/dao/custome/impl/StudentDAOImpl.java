package lk.ijse.hms_hibernet.dao.custome.impl;

import lk.ijse.hms_hibernet.dao.custome.StudentDAO;
import lk.ijse.hms_hibernet.dao.exception.ConstraintViolationException;
import lk.ijse.hms_hibernet.entity.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public boolean save(Student entity, Session session) throws  ConstraintViolationException{
        boolean save1=false;
        try {
            String save = (String)session.save(entity);
            if(save.equals(entity.getSId())){
                save1=true;
            }
            return save1;
        }catch (Exception e){
//            throw new RuntimeException(e);
            throw new ConstraintViolationException(e);
        }

    }

    @Override
    public boolean update(Student student, Session session) {
        try {
            session.update(student.getSId(),student);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e);
//            throw new ConstraintViolationException(e);
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

    public Student load(String pk,Session session){
        return session.load(Student.class, pk);
    }

    @Override
    public boolean isExist(String sId,Session session) {
        Student student = session.get(Student.class, sId);
        System.out.println("Student : "+student);
        if(student==null){
            return false;
        }
        return true;
    }

    @Override
    public List<Student> getAll(Session session) {
        String hql="from Student";
        Query query = session.createQuery(hql);
        List<Student> list = query.list();
        return list;
    }

    @Override
    public Student get(String pk, Session session) {
        return session.get(Student.class, pk);
    }
}
