package poly.edu.asmjava5.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.asmjava5.domain.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByNameContaining(Pageable pageable, String name);

    Page<Category> findAllByNameLike(String keyword, Pageable pageable);
}
