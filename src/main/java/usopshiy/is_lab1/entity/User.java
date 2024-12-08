package usopshiy.is_lab1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Comparable<User> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank
    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "role_requested")
    private boolean isRoleRequested;

    @Override
    public int compareTo(User user) {
        if (this.id > user.getId()) {
            return 1;
        }
        return (this.id == user.getId() ? 0:-1);
    }
}
