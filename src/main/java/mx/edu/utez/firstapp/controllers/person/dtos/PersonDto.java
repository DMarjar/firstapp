package mx.edu.utez.firstapp.controllers.person.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.firstapp.models.person.Person;
import mx.edu.utez.firstapp.models.user.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonDto {
    private long id;
    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 50)
    private String name;
    @NotEmpty(message = "Surname is required")
    @Size(min = 2, max = 50)
    private String surname;
    private String lastName;
    @NotEmpty
    private String birthdate;
    @NotEmpty
    @Pattern(regexp = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" +
            "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
            "[HM]{1}" +
            "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
            "[B-DF-HJ-NP-TV-Z]{3}" +
            "[0-9A-Z]{1}[0-9]{1}$")
    private String curp;
    @NotEmpty
    private String gender;
    private boolean status = true;
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
