package com.example.ecom.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Base DAO providing common CRUD operations against Hibernate's Session.
 *
 * Concrete DAOs extend this and pass their entity type. They can also
 * add custom query methods on top of these.
 *
 * Subclasses are annotated with @Repository (not this abstract class).
 */
public abstract class GenericDao<T, ID extends Serializable> {

    @Autowired
    protected SessionFactory sessionFactory;

    protected final Class<T> entityClass;

    protected GenericDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /** Convenience: the current Hibernate Session bound to the tx. */
    protected Session session() {
        return sessionFactory.getCurrentSession();
    }

    public T save(T entity) {
        session().persist(entity);
        return entity;
    }

    public T update(T entity) {
        return session().merge(entity);
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(session().get(entityClass, id));
    }

    public List<T> findAll() {
        return session()
                .createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                .getResultList();
    }

    public void delete(T entity) {
        session().remove(entity);
    }

    public void deleteById(ID id) {
        findById(id).ifPresent(this::delete);
    }

    public long count() {
        return session()
                .createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                .getSingleResult();
    }
}