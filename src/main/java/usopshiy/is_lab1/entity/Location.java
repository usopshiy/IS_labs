package usopshiy.is_lab1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="LOCATIONS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Location implements Comparable<Location>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="x")
    private long x;

    @Column(name="y", nullable = false)
    private Double y; //Поле не может быть null

    @Column(name="z")
    private double z;

    @Override
    public int compareTo(Location location) {
        if (this.id > location.getId()) {
            return 1;
        }
        return (this.id == location.getId() ? 0:-1);
    }
}
