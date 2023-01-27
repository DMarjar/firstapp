package mx.edu.utez.firstapp.services.person;

import mx.edu.utez.firstapp.models.person.Person;
import mx.edu.utez.firstapp.models.person.PersonRepository;
import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {
    @Autowired // @Autowired is used to inject the dependency automatically
    private PersonRepository repository;

    @Transactional(readOnly = true) // No data persistence is required, it is sent as a response
    public CustomResponse<List<Person>> getAll() {
        return new CustomResponse<>(this.repository.findAll(), false, 200, "OK");
    }

    @Transactional(rollbackFor = {SQLException.class}) // If an exception is thrown, the transaction is rolled back
    public CustomResponse<Person> insert(Person person) {
        Optional<Person> exists = this.repository.findByCurp(person.getCurp().toUpperCase().trim()); // findByCurp() is used to find a person by CURP and is turned to uppercase and trimmed to avoid errors
        if (exists.isPresent()) {
            return new CustomResponse<>(null, true, 400, "The person already exists");
        }
        return new CustomResponse<>(this.repository.saveAndFlush(person), false, 200, "Person registered correctly!"); // saveAndFlush() is used to save the entity and flush changes instantly
    }
}
