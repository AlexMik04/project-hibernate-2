package main;

import config.SessionHibernateConfig;
import entity.*;
import entity.film.Film;
import entity.film.Rating;
import entity.film_actor.FilmActor;
import entity.film_category.FilmCategory;
import factory.FactoryService;
import service.*;

import java.time.Year;
import java.util.Objects;
import java.util.Set;

public class Main {
    private final ServiceRental serviceRental;
    private final ServiceFilm serviceFilm;
    private final ServiceStore serviceStore;
    private final ServiceCustomer serviceCustomer;
    private final ServiceStaff serviceStaff;
    private final ServiceInventory serviceInventory;

    public Main(FactoryService sf) {
        Objects.requireNonNull(sf, "ServiceFactory can not be null");

        this.serviceRental = sf.getServiceRental();
        this.serviceFilm = sf.getServiceFilm();
        this.serviceStore = sf.getServiceStore();
        this.serviceCustomer = sf.getServiceCustomer();
        this.serviceStaff = sf.getServiceStaff();
        this.serviceInventory = sf.getServiceInventory();
    }

    public static void main(String[] args) {
        try (SessionHibernateConfig config = SessionHibernateConfig.getInstance()) {
            FactoryService sf = FactoryService.getInstance(config.getSessionFactory());

            Main main = new Main(sf);

//            main.createAndSaveCustomerToDB();
//            main.createAndSaveFilmToDB();
//            main.createAndSaveRentalToDB();
            main.customerReturnRental();
        }
    }

    private void createAndSaveCustomerToDB() {
        Customer testCustomer = createTestCustomer();
        serviceCustomer.saveCustomerInDB(testCustomer);
    }

    private void createAndSaveFilmToDB() {
        Film testFilm = createTestFilm();
        serviceFilm.saveFilmInDB(testFilm);
    }

    private void createAndSaveRentalToDB() {
        Rental testRental = createTestRental();
        serviceRental.saveRentalInDB(testRental);
    }

    private void customerReturnRental() {
        Rental testRental = serviceRental.getByIdFromDB(16050);
        serviceRental.returnRental(testRental);
    }

    private Customer createTestCustomer() {
        Store store = serviceStore.getByIdFromDB((short) 1);

        String firstName = "First_TEST";
        String lastName = "Last_TEST";

        Country country = new Country("Ukraine");
        City city = new City("Kyiv", country);

        Address address = new Address(
                "123 Khreschatyk Street",
                "Kyiv",
                city,
                "+380 44 123-45-67"
        );

        return new Customer(store, firstName, lastName, address);
    }

    private Rental createTestRental() {
        Inventory inventory = serviceInventory.getByFilmTitleFromDB("MEMPHIS BELLE");
        Objects.requireNonNull(inventory, "Inventory cannot be null");

        Customer customer = serviceCustomer.getByIdFromDB(130);
        Objects.requireNonNull(customer, "Customer cannot be null");

        Staff staff = serviceStaff.getByIdFromDB((short) 1);
        Objects.requireNonNull(staff, "Staff cannot be null");

        return new Rental(inventory, customer, staff);
    }

    private Film createTestFilm() {
        Film film = new Film("MEMPHIS BELLE", new Language("English"));

        film.setDescription("The story of the final bombing mission of the B-17 Flying Fortress 'Memphis Belle' in World War II.");
        film.setReleaseYear(Year.of(1990));
        film.setLength(107);
        film.setRating(Rating.PG_13);
        film.setSpecialFeatureSet(Set.of("Behind the Scenes", "Commentaries"));

        film.addFilmActor(createFilmActor(film, "MATTHEW", "MODINE"));
        film.addFilmActor(createFilmActor(film, "ERIC", "STOLTZ"));
        film.addFilmActor(createFilmActor(film, "SEAN", "ASTIN"));
        film.addFilmActor(createFilmActor(film, "HARRY", "CONNICK JR."));
        film.addFilmActor(createFilmActor(film, "REED", "DIAMOND"));
        film.addFilmActor(createFilmActor(film, "TATE", "DONOVAN"));
        film.addFilmActor(createFilmActor(film, "JOHN", "LITHGOW"));
        film.addFilmActor(createFilmActor(film, "Daniel Bernard", "SWEENEY"));
        film.addFilmActor(createFilmActor(film, "BILLY", "ZANE"));
        film.addFilmActor(createFilmActor(film, "COURTNEY", "GAINS"));
        film.addFilmActor(createFilmActor(film, "NEIL", "GIUNTOLI"));
        film.addFilmActor(createFilmActor(film, "DAVID", "STRATHAIRN"));
        film.addFilmActor(createFilmActor(film, "JANE", "HORROCKS"));
        film.addFilmActor(createFilmActor(film, "MAC", "MCDONALD"));

        film.addFilmCategory(createFilmCategory(film, "Drama"));

        film.addInventory(createInventory(film, serviceStore.getByIdFromDB((short) 1)));

        return film;
    }

    private FilmActor createFilmActor(Film film, String firstName, String lastName) {
        return new FilmActor(film, new Actor(firstName, lastName));
    }

    private FilmCategory createFilmCategory(Film film, String nameCategory) {
        return new FilmCategory(film, new Category(nameCategory));
    }

    private Inventory createInventory(Film film, Store store) {
        return new Inventory(film, store);
    }
}
