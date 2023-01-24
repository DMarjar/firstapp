package mx.edu.utez.firstapp.models.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.firstapp.models.subcategory.Subcategory;

import javax.persistence.*;
import java.util.List;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "categories") // This tells Hibernate to name the table "categories"
@NoArgsConstructor // This tells Lombok to generate a constructor with no parameters
@AllArgsConstructor // This tells Lombok to generate a constructor with all parameters
@Setter // This tells Lombok to generate setters for all fields
@Getter // This tells Lombok to generate getters for all fields
public class Category {
    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincrement
    private long id;
    @Column(nullable = false, length = 150, unique = true)
    private String name;
    @Column(name = "status", nullable = false, columnDefinition = "tinyint default 1") // This makes the status field not nullable and sets the default value to 1
    private Boolean status;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL) // This makes the relationship between Category and Subcategory (one to many and if the category is deleted, the subcategories are deleted too)
    @JsonIgnore // This prevents the subcategories from being shown when the category is shown
    private List<Subcategory> subcategories;
}
