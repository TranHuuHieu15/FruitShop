package poly.edu.asmjava5.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Categories")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_name", columnDefinition = "nvarchar(100) not null")
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Product> products;
}
