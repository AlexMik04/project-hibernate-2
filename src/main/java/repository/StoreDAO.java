package repository;

import entity.Address;
import entity.Store;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class StoreDAO extends BaseDAO<Store> {
    private static final Logger logger = LoggerFactory.getLogger(StoreDAO.class);

    public StoreDAO() {
        super(Store.class);
    }

    public Store findById(Short id, Session session) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(session, "Session cannot be null");

        Query<Store> query = session.createQuery(
                "SELECT s FROM Store s " +
                        "LEFT JOIN FETCH s.staff " +
                        "LEFT JOIN FETCH s.address " +
                        "WHERE s.id = :id", Store.class);
        query.setParameter("id", id);
        query.setMaxResults(1);

        List<Store> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

    public Store findByAddress(Address address, Session session) {
        Objects.requireNonNull(address, "Address can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        if (address.getId() == null) {
            logger.error("Address ID can not be null");
            throw new IllegalArgumentException("Address ID can not be null");
        }

        Query<Store> query = session.createQuery(
                "SELECT s FROM Store s " +
                        "WHERE s.address.id = :addressId", Store.class);
        query.setParameter("addressId", address.getId());
        query.setMaxResults(1);

        List<Store> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
