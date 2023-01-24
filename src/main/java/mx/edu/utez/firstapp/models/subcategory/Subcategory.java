package mx.edu.utez.firstapp.models.subcategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.firstapp.models.category.Category;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subcategories")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Subcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false, length = 150)
    private String name;
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1") // This makes the status field not nullable and sets the default value to 1
    private boolean status;
    @ManyToOne // This makes the relationship between Category and Subcategory (many to one)
    @JoinColumn(name = "category_id", nullable = false) // This makes the relationship between Category and Subcategory (many to one)
    private Category category;
}
