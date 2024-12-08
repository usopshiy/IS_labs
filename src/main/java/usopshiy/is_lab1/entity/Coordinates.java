package usopshiy.is_lab1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="COORDINATES")
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates implements Comparable<Coordinates> {

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
}
