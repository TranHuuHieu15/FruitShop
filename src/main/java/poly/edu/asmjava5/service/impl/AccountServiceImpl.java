package poly.edu.asmjava5.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import poly.edu.asmjava5.domain.Account;
import poly.edu.asmjava5.repository.AccountRepository;
import poly.edu.asmjava5.service.AccountService;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

//    @Autowired
//    BCryptPasswordEncoder pe;


    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    @Deprecated
    public void deleteInBatch(Iterable<Account> entities) {
        accountRepository.deleteInBatch(entities);
    }

    @Override
    @Deprecated
    public Account getOne(String s) {
        return accountRepository.getOne(s);
    }

    @Override
    @Deprecated
    public Account getById(String s) {
        return accountRepository.getById(s);
    }

    @Override
    public <S extends Account> Optional<S> findOne(Example<S> example) {
        return accountRepository.findOne(example);
    }

    @Override
    public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
        return accountRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Account> long count(Example<S> example) {
        return accountRepository.count(example);
    }

    @Override
    public <S extends Account> boolean exists(Example<S> example) {
        return accountRepository.exists(example);
    }

    @Override
    public <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return accountRepository.findBy(example, queryFunction);
    }

//    @Override
//    public String encode(CharSequence rawPassword) {
//        return bcryptPasswordEncoder.encode(rawPassword);
//    }
//
//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        return bcryptPasswordEncoder.matches(rawPassword, encodedPassword);
//    }
//
//    @Override
//    public boolean upgradeEncoding(String encodedPassword) {
//        return bcryptPasswordEncoder.upgradeEncoding(encodedPassword);
//    }
//
//    @Autowired
//    private BCryptPasswordEncoder bcryptPasswordEncoder;
//
//    @Override
//    public Account login(String username, String password) {
//        Optional<Account> optExist = findById(username);
//
//        if (optExist.isPresent()) {
//            Account account = optExist.get();
//            String hashedPassword = account.getPassword();
//            if (bcryptPasswordEncoder.matches(password, hashedPassword)) {
//                optExist.get().setPassword("");
//                return optExist.get();
//            }
//        }
//        System.out.println(bcryptPasswordEncoder.matches(password, optExist.get().getPassword()));
//        return null;
//    }

    @Override
    public Page<Account> findAllByUsernameLike(String keyword, Pageable pageable) {
        return accountRepository.findAllByUsernameLike(keyword, pageable);
    }

    @Override
    public void flush() {
        accountRepository.flush();
    }

    @Override
    public <S extends Account> S saveAndFlush(S entity) {
        return accountRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
        return accountRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Account> entities) {
        accountRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {
        accountRepository.deleteAllByIdInBatch(strings);
    }

    @Override
    public void deleteAllInBatch() {
        accountRepository.deleteAllInBatch();
    }

    @Override
    public Account getReferenceById(String s) {
        return accountRepository.getReferenceById(s);
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example) {
        return accountRepository.findAll(example);
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
        return accountRepository.findAll(example, sort);
    }

    @Override
    public <S extends Account> List<S> saveAll(Iterable<S> entities) {
        return accountRepository.saveAll(entities);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> findAllById(Iterable<String> strings) {
        return accountRepository.findAllById(strings);
    }

    @Override
    public <S extends Account> S save(S entity) {
        return accountRepository.save(entity);
    }

    @Override
    public Optional<Account> findById(String s) {
        return accountRepository.findById(s);
    }

    @Override
    public boolean existsById(String s) {
        return accountRepository.existsById(s);
    }

    @Override
    public long count() {
        return accountRepository.count();
    }

    @Override
    public void deleteById(String s) {
        accountRepository.deleteById(s);
    }

    @Override
    public void delete(Account entity) {
        accountRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        accountRepository.deleteAllById(strings);
    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {
        accountRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        accountRepository.deleteAll();
    }

    @Override
    public List<Account> findAll(Sort sort) {
        return accountRepository.findAll(sort);
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

//    @Override
//    public Account resetPassword(String email) {
//        Account account = accountRepository.findByEmail(email);
//        if (account != null) {
//            // Công thức (Math.random()) * ((max - min) + 1) + min
//            int max = 9999;
//            int min = 1000;
//            String newPassword = String.valueOf((int) (Math.random() * ((max - min) + 1)) + min);
//            account.setPassword(pe.encode(newPassword));
//            accountRepository.save(account);
//            return account;
//        }
//        return null;
//    }

}
