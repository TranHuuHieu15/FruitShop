package poly.edu.asmjava5.service.impl;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import poly.edu.asmjava5.domain.Category;
import poly.edu.asmjava5.repository.CategoryRepository;
import poly.edu.asmjava5.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAllByNameLike(String keyword, Pageable pageable) {
        return categoryRepository.findAllByNameLike(keyword, pageable);
    }

    @Override
    public Page<Category> getCategoryPages(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return categoryRepository.findAll(pageRequest);
    }
    @Override
    public Page<Category> findByNameContaining(Pageable pageable,String name) {
        return categoryRepository.findByNameContaining(pageable, name);
    }

    @Override
    public void flush() {
        categoryRepository.flush();
    }

    @Override
    public <S extends Category> S saveAndFlush(S entity) {
        return categoryRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Category> List<S> saveAllAndFlush(Iterable<S> entities) {
        return categoryRepository.saveAllAndFlush(entities);
    }

    @Override
    public <S extends Category> List<S> saveAll(Iterable<S> entities) {
        return categoryRepository.saveAll(entities);
    }

    //Save
    @Override
    public <S extends Category> S save(S entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public Optional<Category> findById(Long aLong) {
        return categoryRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return categoryRepository.existsById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        categoryRepository.deleteById(aLong);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllById(Iterable<Long> longs) {
        return categoryRepository.findAllById(longs);
    }

    @Override
    public long count() {
        return categoryRepository.count();
    }

    @Override
    public void delete(Category entity) {
        categoryRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        categoryRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Category> entities) {
        categoryRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }

    @Override
    public List<Category> findAll(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public <S extends Category> Optional<S> findOne(Example<S> example) {
        return categoryRepository.findOne(example);
    }

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


}
