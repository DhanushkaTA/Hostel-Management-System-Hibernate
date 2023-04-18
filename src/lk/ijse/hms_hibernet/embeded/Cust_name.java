package lk.ijse.hms_hibernet.embeded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class Cust_name {
    private String f_name;
    private String l_name;
}
