package service;

import entity.Staff;
import factory.FactoryObjects;
import factory.FactoryObjectsDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.StaffDAO;

import java.util.Objects;

public class ServiceStaff {
    private final SessionFactory sessionFactory;

    private final StaffDAO staffDAO;

    public ServiceStaff(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "SessionFactory can not be null");

        FactoryObjects factoryObjectsDAO = FactoryObjectsDAO.getInstance();

        this.staffDAO = factoryObjectsDAO.getObject(StaffDAO.class);
    }

    public Staff getByIdFromDB(Short id) {
        try (Session session = sessionFactory.openSession()) {
            return staffDAO.findById(id, session);
        }
    }
}
