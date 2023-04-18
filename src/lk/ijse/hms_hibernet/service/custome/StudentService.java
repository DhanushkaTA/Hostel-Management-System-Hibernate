package lk.ijse.hms_hibernet.service.custome;

import lk.ijse.hms_hibernet.dto.CustomDTO;
import lk.ijse.hms_hibernet.dto.StudentDTO;
import lk.ijse.hms_hibernet.service.SuperService;
import lk.ijse.hms_hibernet.service.exception.DuplicateException;
import lk.ijse.hms_hibernet.service.exception.NotFoundException;

import java.util.List;

public interface StudentService extends SuperService {
    public boolean saveStudent(StudentDTO studentDTO) throws DuplicateException;

    public List<StudentDTO> getStudentList();

    public  boolean update(StudentDTO studentDTO) throws NotFoundException;

    public boolean delete(String sId);

    public StudentDTO getStudent(String sId);

    public List<CustomDTO> getRemainingStudentList();

    public List<CustomDTO> getStudentListWhoReservedRoom();
}
