package poly.edu.asmjava5.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import poly.edu.asmjava5.domain.Account;
import poly.edu.asmjava5.domain.HistoryOrder;
import poly.edu.asmjava5.domain.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByUsernameLike(String username, Pageable pageable);

    @Transactional
    @Query("select new HistoryOrder (p.image,p.name, a.username , od.quantity, o.amount, o.status) from Account a inner join Order o on a.username = o.username.username inner join OrderDetail od " +
            "on o.orderId = od.order.orderId inner join Product p " +
            "on od.product.productId = p.productId where o.status = 0 and a.username = ?1")
    List<HistoryOrder> findOrderByUsername(String username);
}
