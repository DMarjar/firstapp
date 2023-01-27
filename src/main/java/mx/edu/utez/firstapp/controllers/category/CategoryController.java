package mx.edu.utez.firstapp.controllers.category;

import mx.edu.utez.firstapp.models.category.Category;
import mx.edu.utez.firstapp.services.category.CategoryService;
import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-firstapp/category")
@CrossOrigin(origins = {"*"})
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/")
    // URL: http://localhost:8080/api-firstapp/category/
    public ResponseEntity<CustomResponse<List<Category>>> getAll() {
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK);
    }
}
