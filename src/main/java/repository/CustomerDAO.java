package repository;

import dto.CustomerInfoDTO;
import entity.Address;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class CustomerDAO extends BaseDAO<Customer> {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);

    public CustomerDAO() {
        super(Customer.class);
    }

    public Customer findByName(String firstName, String lastName, Session session) {
        Objects.requireNonNull(firstName, "FirstName can not be null");
        Objects.requireNonNull(lastName, "LastName can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<Customer> query = session.createQuery(
                "SELECT c FROM Customer c " +
                        "WHERE c.firstName = :firstName " +
                        "AND c.lastName = :lastName", Customer.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setMaxResults(1);

        List<Customer> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

    public Customer findByAddressCityCountry(Customer customer, Address address, Session session) {
        Objects.requireNonNull(customer, "Customer can not be null");
        Objects.requireNonNull(address, "Address can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        String firstName = customer.getFirstName();
        Objects.requireNonNull(firstName, "FirstName can not be null");

        String lastName = customer.getLastName();
        Objects.requireNonNull(lastName, "LastName can not be null");

        String addressName = customer.getAddress().getAddress();
        Objects.requireNonNull(addressName, "Address can not be null");

        String cityName = customer.getAddress().getCity().getCity();
        Objects.requireNonNull(cityName, "City can not be null");

        String countryName = customer.getAddress().getCity().getCountry().getCountry();
        Objects.requireNonNull(countryName, "Country can not be null");

        Query<Customer> query = session.createQuery(
                "SELECT c FROM Customer c " +
                        "LEFT JOIN FETCH c.address a " +
                        "LEFT JOIN FETCH a.city c2 " +
                        "LEFT JOIN FETCH c2.country c3 " +
                        "WHERE c.firstName = :firstName " +
                        "AND c.lastName = :lastName " +
                        "AND a.address = :addressName " +
                        "AND c2.city = :cityName " +
                        "AND c3.country = :countryName", Customer.class);

        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("addressName", addressName);
        query.setParameter("cityName", cityName);
        query.setParameter("countryName", countryName);
        query.setMaxResults(1);

        var results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

    public List<CustomerInfoDTO> getItemsCustomerInfoDTO(int offset, int limit, Session session) {
        Objects.requireNonNull(session, "Session cannot be null");

        if (offset < 0) {
            logger.error("Offset cannot be < 0");
            throw new IllegalArgumentException("Offset cannot be < 0");
        }

        if (limit < 0) {
            logger.error("Limit cannot be < 0");
            throw new IllegalArgumentException("Limit cannot be < 0");
        }

        return session.createNamedQuery("CustomerInfoDTOMapping", CustomerInfoDTO.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
