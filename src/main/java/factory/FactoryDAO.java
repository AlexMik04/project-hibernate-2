package factory;

import repository.*;

public class FactoryDAO {
    private static FactoryDAO instance;

    private ActorDAO actorDAO;
    private AddressDAO addressDAO;
    private CategoryDAO categoryDAO;
    private CityDAO cityDAO;
    private CountryDAO countryDAO;
    private CustomerDAO customerDAO;
    private FilmActorDAO filmActorDAO;
    private FilmCategoryDAO filmCategoryDAO;
    private FilmDAO filmDAO;
    private FilmTextDAO filmTextDAO;
    private InventoryDAO inventoryDAO;
    private LanguageDAO languageDAO;
    private PaymentDAO paymentDAO;
    private RentalDAO rentalDAO;
    private StaffDAO staffDAO;
    private StoreDAO storeDAO;

    private FactoryDAO( ) {}

    public static FactoryDAO getInstance() {
        if (instance == null) {
            instance = new FactoryDAO();
        }
        return instance;
    }

    public ActorDAO getActorDAO() {
        if (actorDAO == null) {
            actorDAO = new ActorDAO();
        }
        return actorDAO;
    }

    public AddressDAO getAddressDAO() {
        if (addressDAO == null) {
            addressDAO = new AddressDAO();
        }
        return addressDAO;
    }

    public CategoryDAO getCategoryDAO() {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
        return categoryDAO;
    }

    public CityDAO getCityDAO() {
        if (cityDAO == null) {
            cityDAO = new CityDAO();
        }
        return cityDAO;
    }

    public CountryDAO getCountryDAO() {
        if (countryDAO == null) {
            countryDAO = new CountryDAO();
        }
        return countryDAO;
    }

    public CustomerDAO getCustomerDAO() {
        if (customerDAO == null) {
            customerDAO = new CustomerDAO();
        }
        return customerDAO;
    }

    public FilmActorDAO getFilmActorDAO() {
        if (filmActorDAO == null) {
            filmActorDAO = new FilmActorDAO();
        }
        return filmActorDAO;
    }

    public FilmCategoryDAO getFilmCategoryDAO() {
        if (filmCategoryDAO == null) {
            filmCategoryDAO = new FilmCategoryDAO();
        }
        return filmCategoryDAO;
    }

    public FilmDAO getFilmDAO() {
        if (filmDAO == null) {
            filmDAO = new FilmDAO();
        }
        return filmDAO;
    }

    public FilmTextDAO getFilmTextDAO() {
        if (filmTextDAO == null) {
            filmTextDAO = new FilmTextDAO();
        }
        return filmTextDAO;
    }

    public InventoryDAO getInventoryDAO() {
        if (inventoryDAO == null) {
            inventoryDAO = new InventoryDAO();
        }
        return inventoryDAO;
    }

    public LanguageDAO getLanguageDAO() {
        if (languageDAO == null) {
            languageDAO = new LanguageDAO();
        }
        return languageDAO;
    }

    public PaymentDAO getPaymentDAO() {
        if (paymentDAO == null) {
            paymentDAO = new PaymentDAO();
        }
        return paymentDAO;
    }

    public RentalDAO getRentalDAO() {
        if (rentalDAO == null) {
            rentalDAO = new RentalDAO();
        }
        return rentalDAO;
    }

    public StaffDAO getStaffDAO() {
        if (staffDAO == null) {
            staffDAO = new StaffDAO();
        }
        return staffDAO;
    }

    public StoreDAO getStoreDAO() {
        if (storeDAO == null) {
            storeDAO = new StoreDAO();
        }
        return storeDAO;
    }
}

