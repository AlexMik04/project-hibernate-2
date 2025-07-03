package service;

import entity.Store;
import factory.FactoryObjects;
import factory.FactoryObjectsDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.*;

import java.util.Objects;

public class ServiceStore {
    private final SessionFactory sessionFactory;

    private final StoreDAO storeDAO;

    public ServiceStore(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "SessionFactory can not be null");

        FactoryObjects factoryObjectsDAO = FactoryObjectsDAO.getInstance();

        this.storeDAO = factoryObjectsDAO.getObject(StoreDAO.class);
    }

    public Store getByIdFromDB(Short id) {
        try (Session session = sessionFactory.openSession()) {
            return storeDAO.findById(id, session);
        }
    }
}
