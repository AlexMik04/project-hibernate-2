package repository;

import entity.Actor;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class ActorDAO extends BaseDAO<Actor> {
    public ActorDAO() {
        super(Actor.class);
    }

    public Actor findByName(String firstName, String lastName, Session session) {
        Objects.requireNonNull(firstName, "FirstName can not be null");
        Objects.requireNonNull(lastName, "LastName can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<Actor> query = session.createQuery(
                "SELECT a FROM Actor a " +
                        "WHERE a.firstName = :firstName " +
                        "AND a.lastName = :lastName", Actor.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setMaxResults(1);

        List<Actor> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
