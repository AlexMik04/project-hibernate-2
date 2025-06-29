package repository;

import entity.Country;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class CountryDAO extends BaseDAO<Country> {
    public CountryDAO() {
        super(Country.class);
    }

    public Country findByName(String countryName, Session session) {
        Objects.requireNonNull(countryName, "Country can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<Country> query = session.createQuery(
                "SELECT c FROM Country c " +
                        "WHERE c.country = :country", Country.class);
        query.setParameter("country", countryName);
        query.setMaxResults(1);

        List<Country> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
