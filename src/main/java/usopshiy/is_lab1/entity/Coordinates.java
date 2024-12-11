package usopshiy.is_lab1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="COORDINATES")
@AllArgsConstructor
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

    @Override
    public String toString() {
        return id + " " + x + " " + y;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Coordinates && ((Coordinates) o).getId() == id;
    }
}
