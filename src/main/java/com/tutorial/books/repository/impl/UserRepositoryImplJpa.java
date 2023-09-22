package com.tutorial.books.repository.impl;

import com.tutorial.books.entity.Book_;
import com.tutorial.books.entity.User;
import com.tutorial.books.entity.User_;
import com.tutorial.books.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
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
        var cb = entityManager.getCriteriaBuilder();
        var query = cb.createQuery(User.class);
        var root = query.from(User.class);

        var usersWithBookSubQuery = query.subquery(Integer.class);
        var usersWithBookSubQueryRoot = usersWithBookSubQuery.from(User.class);
        var subJoin = usersWithBookSubQueryRoot.join(User_.books);
        usersWithBookSubQuery.where(cb.equal(subJoin.get(Book_.id), bookId));
        usersWithBookSubQuery.select(usersWithBookSubQueryRoot.get(User_.id));

        query.where(root.get(User_.id).in(usersWithBookSubQuery).not());
        query.select(root);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userJpaRepository.findByUsername(username);
    }
}
