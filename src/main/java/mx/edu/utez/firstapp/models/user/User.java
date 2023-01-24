package mx.edu.utez.firstapp.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.firstapp.models.person.Person;
import mx.edu.utez.firstapp.models.role.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 50, unique = true)
    private String username;
    @Column(nullable = false, length = 50)
    private String password;
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private boolean status;
    @Column(nullable = false)
    private String lastLogin;
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private boolean blocked;
    @Column(nullable = false)
    private String token;
    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id", referencedColumnName = "id", unique = true)
    private Person person;
    @ManyToMany(mappedBy = "users") // mappedBy is used to indicate that the relationship is owned by the "users" field in the Role entity
    private Set<Role> roles;
}
