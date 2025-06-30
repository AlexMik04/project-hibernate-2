package service;

import entity.*;
import factory.FactoryDAO;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.*;

import java.util.Objects;

public class ServiceCustomer {
    private static final Logger logger = LoggerFactory.getLogger(ServiceCustomer.class);

    private final SessionFactory sessionFactory;

    private final CustomerDAO customerDAO;
    private final StoreDAO storeDAO;
    private final AddressDAO addressDAO;
    private final CountryDAO countryDAO;
    private final CityDAO cityDAO;

    public ServiceCustomer(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "SessionFactory cannot be null");

        FactoryDAO factoryDAO = FactoryDAO.getInstance();

        this.customerDAO = factoryDAO.getCustomerDAO();
        this.storeDAO = factoryDAO.getStoreDAO();
        this.addressDAO = factoryDAO.getAddressDAO();
        this.countryDAO = factoryDAO.getCountryDAO();
        this.cityDAO = factoryDAO.getCityDAO();
    }

    public Customer getByIdFromDB(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return customerDAO.findById(id, session);
        }
    }

    public void saveCustomerInDB(Customer customer) {
        Objects.requireNonNull(customer, "Customer cannot be null");

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Customer dbCustomer = customerDAO.findByAddressCityCountry(customer, customer.getAddress(), session);
                if (dbCustomer != null) {
                    logger.error("Customer with ID '{}' already exists in DB", dbCustomer.getId());
                    throw new IllegalStateException("Customer with ID '" + dbCustomer.getId() + "' already exists in DB");
                }

                processStore(customer, session);
                processAddress(customer, session);

                customerDAO.save(customer, session);

                transaction.commit();

            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                    logger.warn("Transaction rollback");
                }
                logger.error("Failed to create and save customer: {}", e.getMessage());
                throw new PersistenceException("Failed to create and save customer: " + e.getMessage(), e);
            }
        }
    }

    private void processStore(Customer customer, Session session) {
        Store store = customer.getStore();
        Objects.requireNonNull(store, "Customer store cannot be null");

        Store dbStore = storeDAO.findById(store.getId(), session);
        if (dbStore == null) {
            storeDAO.save(store, session);
            dbStore = store;
        }

        dbStore.addCustomer(customer);
    }

    private void processAddress(Customer customer, Session session) {
        Address address = customer.getAddress();
        Objects.requireNonNull(address, "Customer address cannot be null");

        City city = address.getCity();
        Objects.requireNonNull(city, "City cannot be null");

        Country country = city.getCountry();
        Objects.requireNonNull(country, "Country cannot be null");

        Country dbCountry = countryDAO.findByName(country.getCountry(), session);
        if (dbCountry == null) {
            countryDAO.save(country, session);
            dbCountry = country;
        }

        city.setCountry(dbCountry);
        dbCountry.addCity(city);
        City dbCity = cityDAO.findByNameAndCountry(city.getCity(), dbCountry.getCountry(), session);
        if (dbCity == null) {
            cityDAO.save(city, session);
            dbCity = city;
        }

        address.setCity(dbCity);
        Address dbAddress = addressDAO.findByAddressAndCity(address.getAddress(), dbCity.getCity(), session);
        if (dbAddress == null) {
            addressDAO.save(address, session);
            dbAddress = address;
        }

        address.addCustomer(customer);
        customer.setAddress(dbAddress);
    }
}
