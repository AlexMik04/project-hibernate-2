package repository;

import entity.Staff;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Objects;

public class StaffDAO extends BaseDAO<Staff> {
    public StaffDAO() {
        super(Staff.class);
    }

    public Staff findByName(String firstName, String lastName, Session session) {
        Objects.requireNonNull(firstName, "FirstName can not be null");
        Objects.requireNonNull(lastName, "LastName can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<Staff> query = session.createQuery(
                "SELECT a FROM Actor a " +
                        "WHERE a.firstName = :firstName " +
                        "AND a.lastName = :lastName", Staff.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setMaxResults(1);

        List<Staff> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
