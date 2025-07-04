package repository;

import entity.Address;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class AddressDAO extends BaseDAO<Address> {
    public AddressDAO() {
        super(Address.class);
    }

    public Address findById(Integer id, Session session) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(session, "Session cannot be null");

        Query<Address> query = session.createQuery(
                "SELECT a FROM Address a " +
                        "LEFT JOIN FETCH a.city " +
                        "WHERE a.id = :id", Address.class);
        query.setParameter("id", id);
        query.setMaxResults(1);

        List<Address> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

    public Address findByAddressAndCity(String addressName, String cityName, Session session) {
        Objects.requireNonNull(addressName, "Address can not be null");
        Objects.requireNonNull(cityName, "City can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<Address> query = session.createQuery(
                "SELECT a FROM Address a " +
                        "WHERE a.address = :address " +
                        "AND a.city.city = :cityName", Address.class);
        query.setParameter("address", addressName);
        query.setParameter("cityName", cityName);
        query.setMaxResults(1);

        List<Address> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

    public Address findByAddressCityCountry(String addressName, String cityName, String countryName, Session session) {
        Objects.requireNonNull(addressName, "Address cannot be null");
        Objects.requireNonNull(cityName, "City cannot be null");
        Objects.requireNonNull(countryName, "Country cannot be null");
        Objects.requireNonNull(session, "Session cannot be null");

        String hql = "SELECT a, c, cu FROM Address a " +
                "JOIN FETCH a.city c " +
                "JOIN FETCH c.country cu " +
                "WHERE a.address = :addressName " +
                "AND c.city = :cityName " +
                "AND cu.country = :countryName";
        Query<Address> query = session.createQuery(hql, Address.class);
        query.setParameter("addressName", addressName);
        query.setParameter("cityName", cityName);
        query.setParameter("countryName", countryName);
        query.setMaxResults(1);

        List<Address> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

}
