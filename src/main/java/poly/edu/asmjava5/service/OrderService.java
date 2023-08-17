package poly.edu.asmjava5.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import poly.edu.asmjava5.domain.Account;
import poly.edu.asmjava5.domain.HistoryOrder;
import poly.edu.asmjava5.domain.Order;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface OrderService {

    @Query("select new HistoryOrder (p.image,p.name, a.username , od.quantity, o.amount, o.status) from Account a inner join Order o on a.username = o.username.username inner join OrderDetail od " +
            "on o.orderId = od.order.orderId inner join Product p " +
            "on od.product.productId = p.productId where o.status = 0 and a.username = ?1")
    @Transactional
    List<HistoryOrder> findOrderByUsername(String username);

    Page<Order> findAllByUsernameLike(String username, Pageable pageable);

    void flush();

    <S extends Order> S saveAndFlush(S entity);

    <S extends Order> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<Order> entities);

    void deleteAllInBatch(Iterable<Order> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    Order getOne(Long aLong);

    @Deprecated
    Order getById(Long aLong);

    Order getReferenceById(Long aLong);

    <S extends Order> List<S> findAll(Example<S> example);

    <S extends Order> List<S> findAll(Example<S> example, Sort sort);

    <S extends Order> List<S> saveAll(Iterable<S> entities);

    List<Order> findAll();

    List<Order> findAllById(Iterable<Long> longs);

    <S extends Order> S save(S entity);

    Optional<Order> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Order entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Order> entities);

    void deleteAll();

    List<Order> findAll(Sort sort);

    Page<Order> findAll(Pageable pageable);

    <S extends Order> Optional<S> findOne(Example<S> example);

    <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Order> long count(Example<S> example);

    <S extends Order> boolean exists(Example<S> example);

    <S extends Order, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
