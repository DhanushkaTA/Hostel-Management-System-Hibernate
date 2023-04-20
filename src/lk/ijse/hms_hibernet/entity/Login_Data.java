package lk.ijse.hms_hibernet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "Login_Data")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Login_Data implements SuperEntity{

    @Id
    private String loginId;

    @Column(name = "logInDate")
    private String logInDate;

    @Column(name = "loginTime")
    private String loginTime;

    @Column(name = "logOutDate")
    private String logOutDate;

    @Column(name = "logOutTime")
    private String logOutTime;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_Id")
    @ToString.Exclude
    private User user;



}
