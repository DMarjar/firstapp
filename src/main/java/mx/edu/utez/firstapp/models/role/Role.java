package mx.edu.utez.firstapp.models.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.firstapp.models.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 15)
    private String name;
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private boolean status;
    @ManyToMany
    @JoinTable( // JoinTable is used to create a new table to store the relationship between the two entities
            name = "user_roles", // The name of the new table
            joinColumns = @JoinColumn(name = "user_id"), // The column that will store the id of the entity that owns the relationship
            inverseJoinColumns = @JoinColumn(name = "role_id") // The column that will store the id of the entity that is being referenced
    )
    @JsonIgnore
    private Set<User> users;
}
