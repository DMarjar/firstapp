package mx.edu.utez.firstapp.services.person;

import mx.edu.utez.firstapp.models.person.Person;
import mx.edu.utez.firstapp.models.person.PersonRepository;
import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {
    @Autowired // @Autowired is used to inject the dependency automatically
    private PersonRepository repository;
    @Transactional(readOnly = true) // No data persistence is required, it is sent as a response
    public CustomResponse<List<Person>> getAll() {
        return new CustomResponse<>(this.repository.findAll(), false, 200, "OK");
    }
}
