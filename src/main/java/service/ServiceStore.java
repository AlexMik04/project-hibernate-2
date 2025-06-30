package service;

import entity.Store;
import factory.FactoryDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.*;

import java.util.Objects;

public class ServiceStore {
    private final SessionFactory sessionFactory;

    private final StoreDAO storeDAO;

    public ServiceStore(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "SessionFactory can not be null");

        FactoryDAO factoryDAO = FactoryDAO.getInstance();

        this.storeDAO = factoryDAO.getStoreDAO();
    }

    public Store getByIdFromDB(Short id) {
        try (Session session = sessionFactory.openSession()) {
            return storeDAO.findById(id, session);
        }
    }
}
