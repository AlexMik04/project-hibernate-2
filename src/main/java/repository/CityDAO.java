package repository;

import entity.City;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class CityDAO extends BaseDAO<City> {
    private static final Logger logger = LoggerFactory.getLogger(CityDAO.class);

    public CityDAO() {
        super(City.class);
    }

    public City findByName(String cityName, Session session) {
        Objects.requireNonNull(cityName, "City can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<City> query = session.createQuery(
                "SELECT c FROM City c " +
                        "WHERE c.city = :cityName", City.class);
        query.setParameter("cityName", cityName);
        query.setMaxResults(1);

        List<City> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

    public City findByNameAndCountry(String cityName, String countryName, Session session) {
        Objects.requireNonNull(cityName, "City can not be null");
        Objects.requireNonNull(countryName, "Country can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<City> query = session.createQuery(
                "SELECT c FROM City c " +
                        "WHERE c.city = :cityName " +
                        "AND c.country.country = :countryName", City.class);
        query.setParameter("cityName", cityName);
        query.setParameter("countryName", countryName);
        query.setMaxResults(1);

        List<City> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
