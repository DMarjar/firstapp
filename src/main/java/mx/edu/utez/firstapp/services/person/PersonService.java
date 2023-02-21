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

    // Get all persons
    @Transactional(readOnly = true) // No data persistence is required, it is sent as a response
    public CustomResponse<List<Person>> getAll() {
        return new CustomResponse<>(this.repository.findAll(), false, 200, "OK");
    }

    // Insert person
    @Transactional(rollbackFor = {SQLException.class}) // If an exception is thrown, the transaction is rolled back
    public CustomResponse<Person> insert(Person person) {
        Optional<Person> exists = this.repository.findByCurp(person.getCurp().toUpperCase().trim()); // findByCurp() is used to find a person by CURP and is turned to uppercase and trimmed to avoid errors
        if (exists.isPresent()) {
            return new CustomResponse<>(null, true, 400, "The person already exists");
        }
        return new CustomResponse<>(this.repository.saveAndFlush(person), false, 200, "Person registered correctly!"); // saveAndFlush() is used to save the entity and flush changes instantly
    }

    // Get one person
    @Transactional(readOnly = true)
    public CustomResponse<Person> getOne(Long id) {
        Optional<Person> exists = this.repository.findById(id);
        if (exists.isEmpty()) {
            return new CustomResponse<>(null, true, 400, "The person does not exist");
        }
        return new CustomResponse<>(exists.get(), false, 200, "OK");
    }

    // Update person
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Person> update(Person person) {
        Optional<Person> exists = this.repository.findById(person.getId());
        if (exists.isEmpty()) {
            return new CustomResponse<>(null, true, 400, "The person does not exist");
        }
        return new CustomResponse<>(this.repository.saveAndFlush(person), false, 200, "Person updated correctly!");
    }

    // Change status person
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> changeStatus(Person person) {
        Optional<Person> exists = this.repository.findById(person.getId());
        if (exists.isEmpty()) {
            return new CustomResponse<>(null, true, 400, "The person does not exist");
        }
        person.setStatus(!person.isStatus());
        this.repository.saveAndFlush(person);
        return new CustomResponse<>(person.isStatus(), false, 200, "Person status changed correctly!");
    }

    // Delete person
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Boolean> delete(Long id) {
        Optional<Person> exists = this.repository.findById(id);
        if (exists.isEmpty()) {
            return new CustomResponse<>(null, true, 400, "The person does not exist");
        }
        this.repository.deleteById(id);
        return new CustomResponse<>(true, false, 200, "Person deleted correctly!");
    }
}
