package poly.edu.asmjava5.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import poly.edu.asmjava5.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> findAllByNameLike(String keyword, Pageable pageable);

    Page<Product> findByNameContaining(Pageable pageable, String name);

    void flush();

    <S extends Product> S saveAndFlush(S entity);

    <S extends Product> List<S> saveAllAndFlush(Iterable<S> entities);

    <S extends Product> List<S> findAll(Example<S> example);

    <S extends Product> List<S> findAll(Example<S> example, Sort sort);

    <S extends Product> List<S> saveAll(Iterable<S> entities);

    List<Product> findAll();

    List<Product> findAllById(Iterable<Long> longs);

    <S extends Product> S save(S entity);

    Optional<Product> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Product entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Product> entities);

    void deleteAll();

    List<Product> findAll(Sort sort);

    Page<Product> findAll(Pageable pageable);

    <S extends Product> Optional<S> findOne(Example<S> example);

    <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Product> long count(Example<S> example);
}
