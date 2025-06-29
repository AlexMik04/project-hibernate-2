package repository;

import entity.Actor;
import entity.film.Film;
import entity.film_actor.FilmActor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class FilmActorDAO extends BaseDAO<FilmActor> {
    private static final Logger logger = LoggerFactory.getLogger(FilmActorDAO.class);

    public FilmActorDAO() {
        super(FilmActor.class);
    }

    public FilmActor findByFilmAndActor(Film film, Actor actor, Session session) {
        Objects.requireNonNull(film, "Film can not be null");
        Objects.requireNonNull(actor, "Actor can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        if (film.getTitle() == null) {
            logger.error("Film Title can not be null");
            throw new IllegalArgumentException("Film Title can not be null");
        }

        if (actor.getFirstName() == null) {
            logger.error("Actor FirstName can not be null");
            throw new IllegalArgumentException("Actor FirstName can not be null");
        }

        if (actor.getLastName() == null) {
            logger.error("Actor LastName can not be null");
            throw new IllegalArgumentException("Actor LastName can not be null");
        }

        Query<FilmActor> query = session.createQuery(
                "SELECT fa FROM FilmActor fa " +
                        "WHERE fa.film.title = :filmTitle " +
                        "AND fa.actor.firstName = :actorFirstName " +
                        "AND fa.actor.lastName = :actorLastName", FilmActor.class);
        query.setParameter("filmTitle", film.getTitle());
        query.setParameter("actorFirstName", actor.getFirstName());
        query.setParameter("actorLastName", actor.getLastName());
        query.setMaxResults(1);

        List<FilmActor> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
