package lk.ijse.hms_hibernet.entity;

import lk.ijse.hms_hibernet.embeded.Cust_name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "User")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements SuperEntity{

    @Id
    @Column(length = 20)
    private String uid;

    @Column(name = "user_name",nullable = false)
    private Cust_name user_name;

    @Column(name = "username",nullable = false,length = 12)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number",length = 10)
    private String phone_number;

    @OneToMany(
            mappedBy = "user",
            targetEntity = Login_Data.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Login_Data> detailList=new ArrayList<>();

    public User(String uid, Cust_name user_name, String username, String password, String phone_number) {
        this.uid = uid;
        this.user_name = user_name;
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
    }
}
