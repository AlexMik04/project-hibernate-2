package repository;

import entity.Inventory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class InventoryDAO extends BaseDAO<Inventory> {
    public InventoryDAO() {
        super(Inventory.class);
    }

    public Inventory findByFilmTitle(String filmTitle, Session session) {
        Objects.requireNonNull(filmTitle, "FilmTitle can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<Inventory> query = session.createQuery(
                "SELECT i FROM Inventory i " +
                        "WHERE i.film.title = :filmTitle", Inventory.class);
        query.setParameter("filmTitle", filmTitle);
        query.setMaxResults(1);

        List<Inventory> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
