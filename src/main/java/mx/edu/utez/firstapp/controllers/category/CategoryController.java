package mx.edu.utez.firstapp.controllers.category;

import mx.edu.utez.firstapp.controllers.category.dtos.CategoryDto;
import mx.edu.utez.firstapp.models.category.Category;
import mx.edu.utez.firstapp.services.category.CategoryService;
import mx.edu.utez.firstapp.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api-firstapp/category")
@CrossOrigin(origins = {"*"})
public class CategoryController {
    @Autowired
    private CategoryService service;

    // GET
    @GetMapping("/")
    // URL: http://localhost:8080/api-firstapp/category/
    public ResponseEntity<CustomResponse<List<Category>>> getAll() {
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK);
    }

    // POST
    @PostMapping("/")
    // URL: http://localhost:8080/api-firstapp/category/
    public ResponseEntity<CustomResponse<Category>> insert(@RequestBody CategoryDto category, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    new CustomResponse<>(null, true, 400, "The category already exists"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                this.service.insert(category.castToCategory()), HttpStatus.CREATED
        );
    }

    // GET ONE
    @PutMapping("/{id}")
    // URL: http://localhost:8080/api-firstapp/category/{id}
    public ResponseEntity<CustomResponse<Category>> update(@PathVariable("id") Long id, @RequestBody CategoryDto category, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    new CustomResponse<>(null, true, 400, "The category already exists"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                this.service.update(category.castToCategory()), HttpStatus.OK
        );
    }

    // UPDATE
    @PatchMapping("/{id}")
    // URL: http://localhost:8080/api-firstapp/category/{id}
    public ResponseEntity<CustomResponse<Boolean>> patch(@PathVariable("id") Long id, @RequestBody CategoryDto category, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    new CustomResponse<>(null, true, 400, "The category already exists"),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(
                this.service.changeStatus(category.castToCategory()), HttpStatus.OK
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    // URL: http://localhost:8080/api-firstapp/category/{id}
    public ResponseEntity<CustomResponse<Boolean>> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                this.service.delete(id), HttpStatus.OK
        );
    }
}
