package mx.edu.utez.firstapp.controllers.person.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.firstapp.models.person.Person;
import mx.edu.utez.firstapp.models.user.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonDto {
    private long id;
    @NotNull
    @NotBlank
    @Length(min = 2, max = 50)
    private String name;
    private String surname;
    private String lastName;
    private String birthdate;
    private String curp;
    private String gender;
    private boolean status;
    private User user;

    public Person getPerson() {
        return new Person(
                getId(),
                getName(),
                getSurname(),
                getLastName(),
                getBirthdate(),
                getCurp(),
                getGender(),
                isStatus(),
                getUser()
        );
    }
}
