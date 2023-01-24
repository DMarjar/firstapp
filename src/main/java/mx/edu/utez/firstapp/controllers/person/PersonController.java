package mx.edu.utez.firstapp.controllers.person;

import mx.edu.utez.firstapp.models.person.Person;
import mx.edu.utez.firstapp.services.person.PersonService;
import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // @RestController is used to create RESTful web services using Spring MVC
@RequestMapping("/api-firstapp/person")
// @RequestMapping is used to map web requests onto specific handler classes and/or handler methods
@CrossOrigin(origins = {"*"})
// @CrossOrigin is used to handle Cross-Origin Resource Sharing (CORS) and is used to allow or restrict requests from a different origin
public class PersonController {
    @Autowired
    private PersonService service;

    @GetMapping("/") // @GetMapping is used to handle the HTTP GET requests matched with given URI expression
    // URL: http://localhost:8080/api-firstapp/person/
    public ResponseEntity<CustomResponse<List<Person>>> // ResponseEntity is used to build a custom response
    getAll() {
        return new ResponseEntity<>(
                this.service.getAll(), // The response body
                HttpStatus.OK); // HttpStatus.OK is used to return the HTTP status code 200
    }
}
