package service;

import entity.Inventory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.*;

import java.util.Objects;

public class ServiceInventory {
    private final SessionFactory sessionFactory;

    private final InventoryDAO inventoryDAO;

    public ServiceInventory(SessionFactory sessionFactory) {
        Objects.requireNonNull(sessionFactory, "SessionFactory cannot be null");

        this.sessionFactory = sessionFactory;

        this.inventoryDAO = new InventoryDAO();
    }

    public Inventory getByIdFromDB(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return inventoryDAO.findById(id, session);
        }
    }

    public Inventory getByFilmTitleFromDB(String filmTitle) {
        try (Session session = sessionFactory.openSession()) {
            return inventoryDAO.findByFilmTitle(filmTitle, session);
        }
    }
}
