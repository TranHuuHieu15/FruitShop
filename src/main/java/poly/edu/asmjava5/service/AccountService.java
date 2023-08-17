package poly.edu.asmjava5.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import poly.edu.asmjava5.domain.Account;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface AccountService {

    Account findByEmail(String email);

    @Deprecated
    void deleteInBatch(Iterable<Account> entities);

    @Deprecated
    Account getOne(String s);

    @Deprecated
    Account getById(String s);

    <S extends Account> Optional<S> findOne(Example<S> example);

    <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Account> long count(Example<S> example);

    <S extends Account> boolean exists(Example<S> example);

    <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);


    Page<Account> findAllByUsernameLike(String keyword, Pageable pageable);

    void flush();

    <S extends Account> S saveAndFlush(S entity);

    <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<Account> entities);

    void deleteAllByIdInBatch(Iterable<String> strings);

    void deleteAllInBatch();

    Account getReferenceById(String s);

    <S extends Account> List<S> findAll(Example<S> example);

    <S extends Account> List<S> findAll(Example<S> example, Sort sort);

    <S extends Account> List<S> saveAll(Iterable<S> entities);

    List<Account> findAll();

    List<Account> findAllById(Iterable<String> strings);

    <S extends Account> S save(S entity);

    Optional<Account> findById(String s);

    boolean existsById(String s);

    long count();

    void deleteById(String s);

    void delete(Account entity);

    void deleteAllById(Iterable<? extends String> strings);

    void deleteAll(Iterable<? extends Account> entities);

    void deleteAll();

    List<Account> findAll(Sort sort);

    Page<Account> findAll(Pageable pageable);

}
