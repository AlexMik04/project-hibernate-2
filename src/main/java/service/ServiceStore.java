package service;

import entity.Store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.*;

import java.util.Objects;

public class ServiceStore {
    private final SessionFactory sessionFactory;

    private final StoreDAO storeDAO;

    public ServiceStore(SessionFactory sessionFactory) {
        Objects.requireNonNull(sessionFactory, "SessionFactory can not be null");

        this.sessionFactory = sessionFactory;

        this.storeDAO = new StoreDAO();
    }

    public Store getByIdFromDB(Short id) {
        try (Session session = sessionFactory.openSession()) {
            return storeDAO.findById(id, session);
        }
    }
}
