package lk.ijse.hms_hibernet.service.custome.impl;

import lk.ijse.hms_hibernet.dao.DaoFactory;
import lk.ijse.hms_hibernet.dao.DaoTypes;
import lk.ijse.hms_hibernet.dao.custome.QueryDAO;
import lk.ijse.hms_hibernet.dao.custome.StudentDAO;
import lk.ijse.hms_hibernet.dao.custome.impl.RoomDAOImpl;
import lk.ijse.hms_hibernet.db.FactoryConfiguration;
import lk.ijse.hms_hibernet.dto.CustomDTO;
import lk.ijse.hms_hibernet.dto.StudentDTO;
import lk.ijse.hms_hibernet.entity.CustomEntity;
import lk.ijse.hms_hibernet.service.util.Convertor;
import lk.ijse.hms_hibernet.service.custome.StudentService;
import lk.ijse.hms_hibernet.service.exception.DuplicateException;
import lk.ijse.hms_hibernet.service.exception.NotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    public final static StudentDAO studentDAO=
            (StudentDAO) DaoFactory.getDaoFactory().getDAO(DaoTypes.STUDENT);

    public final QueryDAO queryDAO=
            (QueryDAO) DaoFactory.getDaoFactory().getDAO(DaoTypes.QUERY);

    @Override
    public boolean saveStudent(StudentDTO studentDTO) throws DuplicateException{
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        if(studentDAO.isExist(studentDTO.getSId(),session)){
            throw new DuplicateException("Customer already saved Or This ID number already used");
        }


        boolean save=false;
        try {
            save = studentDAO.save(Convertor.toStudent(studentDTO), session);
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
    public List<StudentDTO> getStudentList() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return studentDAO.getAll(session).
                    stream().map(student -> Convertor.toStudentDTO(student)).collect(Collectors.toList());
        }finally {
            session.close();
        }
    }

    @Override
    public boolean update(StudentDTO studentDTO) throws NotFoundException{
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
//            if(!studentDAO.isExist(studentDTO.getSId(),session)){
//                throw new NotFoundException("Student not found! Please check details again");
//            }
            boolean isUpdate = studentDAO.update(Convertor.toStudent(studentDTO), session);
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
    public boolean delete(String sId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            boolean exist = studentDAO.isExist(sId, session);
            if (!exist){
                throw new NotFoundException("Student not found! Please check details again");
            }

            boolean isDelete = studentDAO.delete(sId, session);
            transaction.commit();
            return isDelete;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public StudentDTO getStudent(String sId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return Convertor.toStudentDTO(studentDAO.get(sId, session));
        }finally {
            session.close();
        }
    }

    @Override
    public List<CustomDTO> getRemainingStudentList() {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return queryDAO.getRemainingStudentList(session).
                    stream().map(customEntity -> Convertor.toCustomDTO(customEntity)).collect(Collectors.toList());
        }finally {
            session.close();
        }
    }

    public List<CustomDTO> getStudentListWhoReservedRoom(){
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            return queryDAO.getStudentListWhoReservedRoom(session).
                    stream().map(customEntity -> Convertor.toCustomStudentDTO(customEntity)).collect(Collectors.toList());
        }finally {
            session.close();
        }
    }

}
