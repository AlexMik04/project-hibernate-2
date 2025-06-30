package factory;

import org.hibernate.SessionFactory;
import service.*;

import java.util.Objects;

public class FactoryService {
    private static FactoryService instance;

    private final SessionFactory sessionFactory;

    private ServiceCustomer serviceCustomer;
    private ServiceFilm serviceFilm;
    private ServiceInventory serviceInventory;
    private ServiceRental serviceRental;
    private ServiceStaff serviceStaff;
    private ServiceStore serviceStore;

    private FactoryService(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "SessionFactory cannot be null");
    }

    public static FactoryService getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new FactoryService(sessionFactory);
        }
        return instance;
    }

    public ServiceCustomer getServiceCustomer() {
        if (serviceCustomer == null) {
            serviceCustomer = new ServiceCustomer(sessionFactory);
        }
        return serviceCustomer;
    }

    public ServiceFilm getServiceFilm() {
        if (serviceFilm == null) {
            serviceFilm = new ServiceFilm(sessionFactory);
        }
        return serviceFilm;
    }

    public ServiceInventory getServiceInventory() {
        if (serviceInventory == null) {
            serviceInventory = new ServiceInventory(sessionFactory);
        }
        return serviceInventory;
    }

    public ServiceRental getServiceRental() {
        if (serviceRental == null) {
            serviceRental = new ServiceRental(sessionFactory);
        }
        return serviceRental;
    }

    public ServiceStaff getServiceStaff() {
        if (serviceStaff == null) {
            serviceStaff = new ServiceStaff(sessionFactory);
        }
        return serviceStaff;
    }

    public ServiceStore getServiceStore() {
        if (serviceStore == null) {
            serviceStore = new ServiceStore(sessionFactory);
        }
        return serviceStore;
    }
}
