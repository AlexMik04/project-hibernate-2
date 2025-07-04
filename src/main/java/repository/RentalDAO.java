package repository;

import dto.RentalInfoDTO;
import entity.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class RentalDAO extends BaseDAO<Rental> {
    private static final Logger logger = LoggerFactory.getLogger(RentalDAO.class);

    public RentalDAO() {
        super(Rental.class);
    }

    public boolean isInventoryAvailable(Inventory inventory, Session session) {
        Objects.requireNonNull(inventory, "Inventory cannot be null");
        Objects.requireNonNull(session, "Session cannot be null");

        Long count = session.createQuery(
                        "SELECT COUNT(r) FROM Rental r " +
                                "WHERE r.inventory = :inventory " +
                                "AND r.returnDate IS NULL", Long.class)
                .setParameter("inventory", inventory)
                .uniqueResult();

        return count == 0;
    }

    public Rental findById(Integer id, Session session) {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(session, "Session cannot be null");

        Query<Rental> query = session.createQuery(
                "SELECT r FROM Rental r " +
                        "LEFT JOIN FETCH r.inventory " +
                        "LEFT JOIN FETCH r.customer " +
                        "LEFT JOIN FETCH r.staff " +
                        "WHERE r.id = :id", Rental.class);
        query.setParameter("id", id);
        query.setMaxResults(1);

        List<Rental> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

    public List<RentalInfoDTO> getItemsRentalInfoDTO(int offset, int limit, Session session) {
        Objects.requireNonNull(session, "Session cannot be null");

        if (offset < 0) {
            logger.error("Offset cannot be < 0");
            throw new IllegalArgumentException("Offset cannot be < 0");
        }

        if (limit < 0) {
            logger.error("Limit cannot be < 0");
            throw new IllegalArgumentException("Limit cannot be < 0");
        }

        return session.createNamedQuery("RentalInfoDTOMapping", RentalInfoDTO.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
