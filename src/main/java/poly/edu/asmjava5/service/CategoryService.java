package poly.edu.asmjava5.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import poly.edu.asmjava5.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Page<Category> findAllByNameLike(String keyword, Pageable pageable);

    Page<Category> getCategoryPages(int page, int size);

    Page<Category> findByNameContaining(Pageable pageable, String name);

    void flush();

    <S extends Category> S saveAndFlush(S entity);

    <S extends Category> List<S> saveAllAndFlush(Iterable<S> entities);

    <S extends Category> List<S> saveAll(Iterable<S> entities);

    //Save
    <S extends Category> S save(S entity);

    Optional<Category> findById(Long aLong);

    boolean existsById(Long aLong);

    void deleteById(Long aLong);

    List<Category> findAll();

    List<Category> findAllById(Iterable<Long> longs);

    long count();

    void delete(Category entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Category> entities);

    void deleteAll();

    List<Category> findAll(Sort sort);

    Page<Category> findAll(Pageable pageable);

    <S extends Category> Optional<S> findOne(Example<S> example);
}
