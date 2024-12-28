package usopshiy.is_lab1.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="COORDINATES")
@EqualsAndHashCode
public class Coordinates implements Comparable<Coordinates>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "x", nullable = false)
    private Float x; //Поле не может быть null

    @Max(291)
    @Column(name = "y")
    private long y; //Максимальное значение поля: 291

    @Override
    public int compareTo(Coordinates coordinates) {
        if (this.id > coordinates.getId()) {
            return 1;
        }
        return (this.id == coordinates.getId() ? 0:-1);
    }

    public Coordinates() {
        this.id = 0;
        this.x = 0F;
        this.y = 0;
    }

    @JsonCreator
    public Coordinates(@JsonProperty("id") long id,
                       @JsonProperty("x") Float x,
                       @JsonProperty("y") long y) {
        this.id = id;
        if (x == null) {
            throw new IllegalArgumentException("x cannot be null");
        }
        this.x = x;
        if (y > 291) {
            throw new IllegalArgumentException("y cannot be greater than 291");
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return id + " " + x + " " + y;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Coordinates && ((Coordinates) o).getId() == id;
    }
}
