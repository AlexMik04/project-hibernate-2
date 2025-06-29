package repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import util.ValidObjects;

import java.util.List;
import java.util.Objects;

public class BaseDAO<T> {
    private final Class<T> entityClass;

    public BaseDAO(Class<T> entityClass) {
        this.entityClass = Objects.requireNonNull(entityClass, "Entity can not be null");
    }

    public void save(T entity, Session session) {
        Objects.requireNonNull(entity, "Entity can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        session.persist(entity);
    }

    public T update(T entity, Session session) {
        Objects.requireNonNull(entity, "Entity can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        return session.merge(entity);
    }

    public <E> T findById(E id, Session session) {
        Objects.requireNonNull(id, "ID can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        if (ValidObjects.isWholeNumberType(id)) {
            Number index = (Number) id;
            if (index.longValue() < 0) {
                throw new IllegalArgumentException("ID can not be < 0");
            }
        }

        return session.find(entityClass, id);
    }

    public List<T> getItems(int offset, int limit, Session session) {
        Objects.requireNonNull(session, "Session can not be null");

        if (offset < 0) {
            throw new IllegalArgumentException("Offset can not be < 0");
        }

        if (limit < 0) {
            throw new IllegalArgumentException("Limit can not be < 0");
        }

        Query<T> query = session.createQuery(
                "SELECT t FROM " + entityClass.getSimpleName() + " t", entityClass);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<T> getAll(Session session) {
        Objects.requireNonNull(session, "Session can not be null");

        Query<T> query = session.createQuery(
                "SELECT t FROM " + entityClass.getSimpleName() + " t", entityClass);
        return query.getResultList();
    }

    public int getTotalCount(Session session) {
        Objects.requireNonNull(session, "Session can not be null");

        Query<Long> query = session.createQuery(
                "SELECT COUNT(t) FROM " + entityClass.getSimpleName() + " t", Long.class);
        return Math.toIntExact(query.uniqueResult());
    }
}
