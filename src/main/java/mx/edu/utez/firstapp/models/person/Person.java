package mx.edu.utez.firstapp.models.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.firstapp.models.user.User;

import javax.persistence.*;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name", nullable = false, length = 50)
    private String name;
    @Column(name = "surname", nullable = false, length = 50)
    private String surname;
    @Column(name = "last_name", length = 50)
    private String lastName;
    @Column(nullable = false, length = 50)
    private String birthdate;
    @Column(nullable = false, length = 50)
    private String curp;
    @Column(nullable = false, columnDefinition = "CHAR(1) DEFAULT 'M'")
    private String gender;
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private boolean status;
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;

    public Person(String name, String surname, String lastName, String birthdate, String curp, String gender, boolean status, User user) {
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.curp = curp;
        this.gender = gender;
        this.status = status;
        this.user = user;
    }
}