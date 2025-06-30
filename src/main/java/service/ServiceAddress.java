package service;

import entity.Address;
import entity.City;
import entity.Country;
import factory.FactoryDAO;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.AddressDAO;
import repository.CityDAO;
import repository.CountryDAO;

import java.util.Objects;

public class ServiceAddress {
    private static final Logger logger = LoggerFactory.getLogger(ServiceAddress.class);

    private final SessionFactory sessionFactory;

    private final AddressDAO addressDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;

    public ServiceAddress(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "SessionFactory can not be null");

        FactoryDAO factoryDAO = FactoryDAO.getInstance();

        this.addressDAO = factoryDAO.getAddressDAO();
        this.cityDAO = factoryDAO.getCityDAO();
        this.countryDAO = factoryDAO.getCountryDAO();
    }

    public Address getByIdFromDB(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return addressDAO.findById(id, session);
        }
    }

    public void saveAddressInDB(Address address) {
        Objects.requireNonNull(address, "Address cannot be null");

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                City city = address.getCity();
                Objects.requireNonNull(city, "City cannot be null");

                Country country = city.getCountry();
                Objects.requireNonNull(country, "Country cannot be null");

                Address dbAddress = addressDAO.findByAddressCityCountry(address.getAddress(), city.getCity(), country.getCountry(), session);
                if (dbAddress != null) {
                    logger.warn("Address ID '{}' already exists in DB", dbAddress.getId());
                    throw new IllegalStateException("Address ID '" + dbAddress.getId() + "' already exists in DB");
                }

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

                addressDAO.save(address, session);

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
}
