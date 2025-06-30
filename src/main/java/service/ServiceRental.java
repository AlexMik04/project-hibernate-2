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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ServiceRental {
    private static final Logger logger = LoggerFactory.getLogger(ServiceRental.class);

    private final SessionFactory sessionFactory;

    private final CustomerDAO customerDAO;
    private final InventoryDAO inventoryDAO;
    private final PaymentDAO paymentDAO;
    private final RentalDAO rentalDAO;
    private final StaffDAO staffDAO;
    private final StoreDAO storeDAO;

    public ServiceRental(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "SessionFactory can not be null");

        FactoryDAO factoryDAO = FactoryDAO.getInstance();

        this.customerDAO = factoryDAO.getCustomerDAO();
        this.inventoryDAO = factoryDAO.getInventoryDAO();
        this.paymentDAO = factoryDAO.getPaymentDAO();
        this.rentalDAO = factoryDAO.getRentalDAO();
        this.staffDAO = factoryDAO.getStaffDAO();
        this.storeDAO = factoryDAO.getStoreDAO();
    }

    public Rental getByIdFromDB(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return rentalDAO.findById(id, session);
        }
    }

    public void returnRental(Rental rental) {
        Objects.requireNonNull(rental, "Rental can not be null");

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                if (rental.getRentalDate() != null) {
                    logger.warn("The Rental has already been returned: " + rental.getRentalDate());
                    throw new IllegalStateException("The Rental has already been returned: " + rental.getRentalDate());
                }
                rental.setReturnDate(LocalDateTime.now());

                rentalDAO.update(rental, session);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                    logger.warn("Transaction rollback");
                }
                logger.error("Failed to return rental: {}", e.getMessage());
                throw new PersistenceException("Failed to return rental: " + e.getMessage(), e);
            }
        }
    }

    public void saveRentalInDB(Rental rental) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Customer dbCustomer = customerDAO.findById(rental.getCustomer().getId(), session);
                Objects.requireNonNull(dbCustomer, "Customer can not be null");

                Store dbStore = storeDAO.findById(rental.getStaff().getStore().getId(), session);
                Objects.requireNonNull(dbStore, "Store can not be null");

                Inventory dbInventory = inventoryDAO.findById(rental.getInventory().getId(), session);
                Objects.requireNonNull(dbInventory, "Inventory can not be null");

                Staff dbStaff = staffDAO.findById(rental.getStaff().getId(), session);
                Objects.requireNonNull(dbStaff, "Staff can not be null");

                boolean isAvailable = rentalDAO.isInventoryAvailable(dbInventory, session);
                if (!isAvailable) {
                    logger.warn("Inventory is currently rented and not returned yet.");
                    throw new IllegalStateException("Inventory is currently rented and not returned yet.");
                }

                rental.setCustomer(dbCustomer);
                rental.setInventory(dbInventory);
                rental.setStaff(dbStaff);
                rental.setRentalDate(LocalDateTime.now());

                rentalDAO.save(rental, session);

                BigDecimal rentalRate = dbInventory.getFilm().getRentalRate();

                Payment payment = new Payment(dbCustomer, dbStaff, rentalRate);
                payment.setRental(rental);
                paymentDAO.save(payment, session);
                dbCustomer.addPayment(payment);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                    logger.warn("Transaction rollback");
                }
                logger.error("Failed to create rental and payment: {}", e.getMessage());
                throw new PersistenceException("Failed to create rental and payment: " + e.getMessage(), e);
            }
        }
    }
}
