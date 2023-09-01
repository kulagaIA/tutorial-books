package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.User;
import com.tutorial.books.entity.User_;
import com.tutorial.books.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImplJpa implements UserRepository {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<User> getAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public Optional<User> getById(Integer id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public List<User> getByBookId(Integer bookId) {
        return userJpaRepository.findByBooksId(bookId);
    }

    @Override
    public User create(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public void update(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public List<User> getWithoutBookByBookId(Integer bookId) {
        return null;
    }

    private Specification<User> nameLike(String name){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get(User_.NAME), "%"+name+"%");
            }
        };
    }
}
