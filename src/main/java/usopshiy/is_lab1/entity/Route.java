package usopshiy.is_lab1.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import usopshiy.is_lab1.comparators.*;

import java.time.LocalDateTime;
import java.util.Comparator;

@Getter
@Setter
@Entity
@Table(name = "ROUTES")
@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @JsonCreator
    public Route(
            @JsonProperty("name") String name,
            @JsonProperty("coordinates") Coordinates coordinates,
            @JsonProperty("from") Location from,
            @JsonProperty("to") Location to,
            @JsonProperty("distance") Integer distance,
            @JsonProperty("rating") int rating
    ) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Route name cannot be null or empty");
        }
        this.name = name;
        if (coordinates == null) {
            throw new IllegalArgumentException("Route coordinates cannot be null");
        }
        this.coordinates = coordinates;
        this.from = from;
        if (to == null) {
            throw new IllegalArgumentException("Route to cannot be null");
        }
        this.to = to;
        if (distance != null && distance <= 1) {
            throw new IllegalArgumentException("Route distance must be greater than 1");
        }
        this.distance = distance;
        if (rating <= 0) {
            throw new IllegalArgumentException("Route rating must be greater than 0");
        }
        this.rating = rating;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name; //Поле не может быть null, Строка не может быть пустой

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coordinates_id", nullable = false)
    private Coordinates coordinates; //Поле не может быть null

    @Column(name = "creation_date", nullable = false)
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "from_location")
    private Location from; //Поле может быть null

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "to_location", nullable = false)
    private Location to; //Поле не может быть null

    @Min(2)
    @Column(name = "distance")
    private Integer distance; //Поле может быть null, Значение поля должно быть больше 1

    @Min(1)
    @Column(name = "rating")
    private int rating; //Значение поля должно быть больше 0

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }

    public static Comparator<Route> getFieldComparator(String field) {
        switch (field){
            case "name":
                return new RouteNameComparator();
            case "coordinates":
                return new RouteCoordinatesComparator();
            case "creationDate":
                return new RouteDateComparator();
            case "from":
                return new RouteFromComparator();
            case "to":
                return new RouteToComparator();
            case "distance":
                return new RouteDistanceComparator();
            case "rating":
                return new RouteRatingComparator();
            case "owner":
                return new RouteOwnerComparator();
            default:
                return new RouteIdComparator();
        }
    }
}
