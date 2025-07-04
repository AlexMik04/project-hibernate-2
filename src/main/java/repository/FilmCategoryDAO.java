package repository;

import entity.Category;
import entity.film.Film;
import entity.film_category.FilmCategory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class FilmCategoryDAO extends BaseDAO<FilmCategory> {
    private static final Logger logger = LoggerFactory.getLogger(FilmCategoryDAO.class);

    public FilmCategoryDAO() {
        super(FilmCategory.class);
    }

    public FilmCategory findByFilmAndCategory(Film film, Category category, Session session) {
        Objects.requireNonNull(film, "Film can not be null");
        Objects.requireNonNull(category, "Category can not be null");
        Objects.requireNonNull(session, "Session can not be null");

        if (film.getTitle() == null) {
            logger.error("Film Title can not be null");
            throw new IllegalArgumentException("Film Title can not be null");
        }

        if (category.getName() == null) {
            logger.error("Category Name can not be null");
            throw new IllegalArgumentException("Category Name can not be null");
        }

        Query<FilmCategory> query = session.createQuery(
                "SELECT fc FROM FilmCategory fc " +
                        "WHERE fc.film.title = :filmTitle " +
                        "AND fc.category.name = :categoryName", FilmCategory.class);
        query.setParameter("filmTitle", film.getTitle());
        query.setParameter("categoryName", category.getName());
        query.setMaxResults(1);

        List<FilmCategory> results = query.getResultList();
        return results.isEmpty() ? null : results.getFirst();
    }
}
