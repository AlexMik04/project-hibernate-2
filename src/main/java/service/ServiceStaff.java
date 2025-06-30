package service;

import entity.Staff;
import factory.FactoryDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.StaffDAO;

import java.util.Objects;

public class ServiceStaff {
    private final SessionFactory sessionFactory;

    private final StaffDAO staffDAO;

    public ServiceStaff(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "SessionFactory can not be null");

        FactoryDAO factoryDAO = FactoryDAO.getInstance();

        this.staffDAO = factoryDAO.getStaffDAO();
    }

    public Staff getByIdFromDB(Short id) {
        try (Session session = sessionFactory.openSession()) {
            return staffDAO.findById(id, session);
        }
    }
}
