package entity.film_category;

import entity.Category;
import entity.film.Film;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "film_category", schema = "movie")
public class FilmCategory {
    @EmbeddedId
    private FilmCategoryId id = new FilmCategoryId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("filmId")
    @JoinColumn(name = "film_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Category category;

    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private LocalDateTime lastUpdate;



    public FilmCategory() {
    }

    public FilmCategory(Film film, Category category) {
        this.film = Objects.requireNonNull(film, "Film can not be null");
        this.category = Objects.requireNonNull(category, "Category can not be null");
    }

    public FilmCategoryId getId() {
        return id;
    }

    public void setId(FilmCategoryId id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
