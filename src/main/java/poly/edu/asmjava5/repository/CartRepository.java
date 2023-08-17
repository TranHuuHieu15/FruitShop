package poly.edu.asmjava5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poly.edu.asmjava5.domain.Cart;

import java.util.List;
import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByProductId(Long productId);
}
