package repository;

import entity.Category;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class CategoryDAO extends BaseDAO<Category> {
    public CategoryDAO() {
        super(Category.class);
    }

    public Category findByName(String name, Session session) {
        Objects.requireNonNull(name, "Name can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<Category> query = session.createQuery(
                "SELECT c FROM Category c " +
                        "WHERE c.name = :name", Category.class);
        query.setParameter("name", name);
        query.setMaxResults(1);

        List<Category> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
