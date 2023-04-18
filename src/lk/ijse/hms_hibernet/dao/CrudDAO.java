package lk.ijse.hms_hibernet.dao;


import lk.ijse.hms_hibernet.dao.exception.ConstraintViolationException;
import lk.ijse.hms_hibernet.entity.SuperEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface CrudDAO<T extends SuperEntity,ID extends Serializable> extends SuperDAO{

    public boolean save(T entity, Session session) throws ConstraintViolationException;
    public  boolean update(T entity, Session session);
    public boolean delete (ID pk, Session session);
    public List<T> getAll(Session session);
    public T get(ID pk, Session session);
    public T load(ID pk, Session session);
    public boolean isExist(ID pk,Session session);

}
