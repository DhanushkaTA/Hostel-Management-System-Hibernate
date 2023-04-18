package lk.ijse.hms_hibernet.entity;

import lk.ijse.hms_hibernet.embeded.Cust_Phone;
import lk.ijse.hms_hibernet.embeded.Cust_name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "student")
public class Student implements SuperEntity{

    @Id
    @Column(name = "sId",nullable = false,length = 20)
    private String sId;

    @Column(name = "name")
    private Cust_name name;

    @Column(name = "address",columnDefinition = "TEXT",length = 255)
    private String address;

    @Column(name = "phoneNum")
    private String phoneNum;

    @Column(name = "dob",columnDefinition = "DATE")
    private String dob;

    @Column(name = "gender",length = 255)
    private String gender;

    @OneToMany(
            mappedBy = "student",
            targetEntity = Reservation.class,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Reservation> reservationList=new ArrayList<>();

    public Student(String sId, Cust_name name, String address, String phoneNum, String dob, String gender) {
        this.sId = sId;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.dob = dob;
        this.gender = gender;
    }
}
