package poly.edu.asmjava5.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import poly.edu.asmjava5.domain.OrderDetail;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface OrderDetailService {
    void flush();

    <S extends OrderDetail> S saveAndFlush(S entity);

    <S extends OrderDetail> List<S> saveAllAndFlush(Iterable<S> entities);

    @Deprecated
    void deleteInBatch(Iterable<OrderDetail> entities);

    void deleteAllInBatch(Iterable<OrderDetail> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    OrderDetail getOne(Long aLong);

    @Deprecated
    OrderDetail getById(Long aLong);

    OrderDetail getReferenceById(Long aLong);

    <S extends OrderDetail> List<S> findAll(Example<S> example);

    <S extends OrderDetail> List<S> findAll(Example<S> example, Sort sort);

    <S extends OrderDetail> List<S> saveAll(Iterable<S> entities);

    List<OrderDetail> findAll();

    List<OrderDetail> findAllById(Iterable<Long> longs);

    <S extends OrderDetail> S save(S entity);

    Optional<OrderDetail> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(OrderDetail entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends OrderDetail> entities);

    void deleteAll();

    List<OrderDetail> findAll(Sort sort);

    Page<OrderDetail> findAll(Pageable pageable);

    <S extends OrderDetail> Optional<S> findOne(Example<S> example);

    <S extends OrderDetail> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends OrderDetail> long count(Example<S> example);

    <S extends OrderDetail> boolean exists(Example<S> example);

    <S extends OrderDetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
