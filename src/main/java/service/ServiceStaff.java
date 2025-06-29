package service;

import entity.Staff;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import repository.StaffDAO;

import java.util.Objects;

public class ServiceStaff {
    private final SessionFactory sessionFactory;

    private final StaffDAO staffDAO;

    public ServiceStaff(SessionFactory sessionFactory) {
        Objects.requireNonNull(sessionFactory, "SessionFactory can not be null");

        this.sessionFactory = sessionFactory;

        this.staffDAO = new StaffDAO();
    }

    public Staff getByIdFromDB(Short id) {
        try (Session session = sessionFactory.openSession()) {
            return staffDAO.findById(id, session);
        }
    }
}
