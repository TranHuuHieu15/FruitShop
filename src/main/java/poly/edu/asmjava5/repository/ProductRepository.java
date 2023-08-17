package poly.edu.asmjava5.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.asmjava5.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContaining(Pageable pageable, String name);
    Page<Product> findAllByNameLike(String keyword, Pageable pageable);
}
