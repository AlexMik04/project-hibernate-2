package repository;

import dto.FilmInfoDTO;
import entity.film.Film;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class FilmDAO extends BaseDAO<Film> {
    private static final Logger logger = LoggerFactory.getLogger(FilmDAO.class);

    public FilmDAO() {
        super(Film.class);
    }

    public Film findByTitle(String title, Session session) {
        Objects.requireNonNull(title, "Title can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        Query<Film> query = session.createQuery(
                "SELECT f FROM Film f " +
                        "WHERE f.title = :title", Film.class);
        query.setParameter("title", title);
        query.setMaxResults(1);

        List<Film> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }

    public List<FilmInfoDTO> getItemsFilmInfoDTO(int offset, int limit, Session session) {
        Objects.requireNonNull(session, "Session cannot be null");

        if (offset < 0) {
            logger.error("Offset cannot be < 0");
            throw new IllegalArgumentException("Offset cannot be < 0");
        }

        if (limit < 0) {
            logger.error("Limit cannot be < 0");
            throw new IllegalArgumentException("Limit cannot be < 0");
        }

        return session.createNamedQuery("FilmInfoDTOMapping", FilmInfoDTO.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
