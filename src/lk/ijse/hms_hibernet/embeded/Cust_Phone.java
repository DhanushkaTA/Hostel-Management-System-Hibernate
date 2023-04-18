package lk.ijse.hms_hibernet.embeded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@ToString
public class Cust_Phone {
    private String phone_num;
    private String type;
}
