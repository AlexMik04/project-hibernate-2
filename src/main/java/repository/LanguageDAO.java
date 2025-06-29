package repository;

import entity.Language;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class LanguageDAO extends BaseDAO<Language> {
    public LanguageDAO() {
        super(Language.class);
    }

    public Language findByName(String name, Session session) {
        Objects.requireNonNull(name, "Name can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<Language> query = session.createQuery(
                "SELECT l FROM Language l " +
                        "WHERE l.name = :name", Language.class);
        query.setParameter("name", name);
        query.setMaxResults(1);

        List<Language> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
