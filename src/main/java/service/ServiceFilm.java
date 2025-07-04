package service;

import entity.*;
import entity.film.Film;
import entity.film_actor.FilmActor;
import entity.film_category.FilmCategory;
import factory.FactoryObjects;
import factory.FactoryObjectsDAO;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.*;

import java.util.*;

public class ServiceFilm {
    private static final Logger logger = LoggerFactory.getLogger(ServiceFilm.class);

    private final SessionFactory sessionFactory;

    private final ActorDAO actorDAO;
    private final CategoryDAO categoryDAO;
    private final FilmActorDAO filmActorDAO;
    private final FilmCategoryDAO filmCategoryDAO;
    private final FilmDAO filmDAO;
    private final InventoryDAO inventoryDAO;
    private final LanguageDAO languageDAO;
    private final StoreDAO storeDAO;
    
    public ServiceFilm(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "SessionFactory can not be null");

        FactoryObjects factoryObjectsDAO = FactoryObjectsDAO.getInstance();

        this.actorDAO = factoryObjectsDAO.getObject(ActorDAO.class);
        this.categoryDAO = factoryObjectsDAO.getObject(CategoryDAO.class);
        this.filmActorDAO = factoryObjectsDAO.getObject(FilmActorDAO.class);
        this.filmCategoryDAO = factoryObjectsDAO.getObject(FilmCategoryDAO.class);
        this.filmDAO = factoryObjectsDAO.getObject(FilmDAO.class);
        this.inventoryDAO = factoryObjectsDAO.getObject(InventoryDAO.class);
        this.languageDAO = factoryObjectsDAO.getObject(LanguageDAO.class);
        this.storeDAO = factoryObjectsDAO.getObject(StoreDAO.class);
    }

    public Film getByIdFromDB(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            return filmDAO.findById(id, session);
        }
    }

    public void saveFilmInDB(Film film) {
        Objects.requireNonNull(film, "Film cannot be null");

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Film dbFilm = filmDAO.findByTitle(film.getTitle(), session);
                if (dbFilm != null) {
                    logger.warn("Film '{}' already exists in DB", film.getTitle());
                    throw new IllegalStateException("Film '" + film.getTitle() + "' already exists in DB");
                }

                processLanguage(film, session);

                processFilmCategory(film, session);
                processFilmActor(film, session);
                processInventory(film, session);

                filmDAO.save(film, session);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                    logger.warn("Transaction rollback");
                }
                logger.error("Failed to save film: {}", e.getMessage());
                throw new PersistenceException("Failed to save film: " + e.getMessage(), e);
            }
        }
    }

    private void processLanguage(Film film, Session session) {
        Language language = film.getLanguage();
        Objects.requireNonNull(language, "Film language cannot be null");

        String languageName = language.getName();
        Objects.requireNonNull(languageName, "Language name cannot be null");

        Language dbLanguage = languageDAO.findByName(languageName, session);
        if (dbLanguage == null) {
            languageDAO.save(language, session);
            dbLanguage = language;
        }

        film.setLanguage(dbLanguage);
        dbLanguage.addLanguageFilm(film);
    }

    private void processFilmCategory(Film film, Session session) {
        Set<FilmCategory> set = new HashSet<>(film.getFilmCategories());
        film.getFilmCategories().clear(); // Чтоб избежать дублирования данных в DB

        for (FilmCategory filmCategory : set) {
            Category category = filmCategory.getCategory();
            if (category == null) {
                logger.error("Category in FilmCategory cannot be null");
                throw new IllegalArgumentException("Category in FilmCategory cannot be null");
            }

            Category dbCategory = categoryDAO.findByName(category.getName(), session);
            if (dbCategory == null) {
                categoryDAO.save(category, session);
                dbCategory = category;
            }

            filmCategory.setCategory(dbCategory);
            filmCategory.setFilm(film);

            FilmCategory dbFilmCategory = filmCategoryDAO.findByFilmAndCategory(film, dbCategory, session);
            if (dbFilmCategory == null) {
                filmCategoryDAO.save(filmCategory, session);
                dbFilmCategory = filmCategory;
            }

            dbCategory.addFilmCategory(dbFilmCategory);
            film.addFilmCategory(dbFilmCategory);
        }
    }

    private void processFilmActor(Film film, Session session) {
        Set<FilmActor> set = new HashSet<>(film.getFilmActors());
        film.getFilmActors().clear(); // Чтоб избежать дублирования данных в DB

        for (FilmActor filmActor : set) {
            Actor actor = filmActor.getActor();
            if (actor == null) {
                logger.error("Actor in FilmActor cannot be null");
                throw new IllegalArgumentException("Actor in FilmActor cannot be null");
            }

            Actor dbActor = actorDAO.findByName(actor.getFirstName(), actor.getLastName(), session);
            if (dbActor == null) {
                actorDAO.save(actor, session);
                dbActor = actor;
            }

            filmActor.setActor(dbActor);
            filmActor.setFilm(film);

            FilmActor dbFilmActor = filmActorDAO.findByFilmAndActor(film, dbActor, session);
            if (dbFilmActor == null) {
                filmActorDAO.save(filmActor, session);
                dbFilmActor = filmActor;
            }

            dbActor.addReplaceFilmActor(dbFilmActor);
            film.addFilmActor(dbFilmActor);
        }
    }


    private void processInventory(Film film, Session session) {
        Set<Inventory> set = new HashSet<>(film.getInventories());
        film.getInventories().clear(); // Чтоб избежать дублирования данных в DB

        for (Inventory inventory : set) {
            Store store = inventory.getStore();
            Objects.requireNonNull(store, "Inventory store cannot be null");

            Store dbStore = storeDAO.findById(store.getId(), session);
            if (dbStore == null) {
                storeDAO.save(store, session);
                dbStore = store;
            }

            inventory.setStore(dbStore);
            inventory.setFilm(film);

            inventoryDAO.save(inventory, session);

            dbStore.addInventory(inventory);
            film.addInventory(inventory);
        }
    }
}
