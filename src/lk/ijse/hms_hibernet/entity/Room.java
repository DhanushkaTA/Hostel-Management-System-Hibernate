package lk.ijse.hms_hibernet.entity;

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
@Table(name = "room")
public class Room implements SuperEntity{

    @Id
    @Column(name = "room_type_id")
    private String room_type_id;

    @Column(name = "type")
    private String type;

    @Column(name = "key_money")
    private String key_money;

    @Column(name = "qty")
    private int qty;

    @OneToMany(
            mappedBy = "room",
            targetEntity = Reservation.class,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Reservation>reservationList=new ArrayList<>();

    public Room(String room_type_id, String type, String key_money, int qty) {
        this.room_type_id = room_type_id;
        this.type = type;
        this.key_money = key_money;
        this.qty = qty;
    }

}
