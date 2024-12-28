package usopshiy.is_lab1.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name="LOCATIONS")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Location implements Comparable<Location>, Serializable {

    private static final long serialVersionUID = 1L;

    @JsonCreator
    public Location(
            @JsonProperty("id") long id,
            @JsonProperty("x") long x,
            @JsonProperty("y") Double y,
            @JsonProperty("z") double z
    ) {
        this.id = id;
        this.x = x;
        if (y == null) {
            throw new IllegalArgumentException("location y cannot be null");
        }
        this.y = y;
        this.z = z;
    }

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

    @Override
    public String toString() {
        return id + " " + x + " " + y + " " + z;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Location && this.id == ((Location) o).id;
    }
}
