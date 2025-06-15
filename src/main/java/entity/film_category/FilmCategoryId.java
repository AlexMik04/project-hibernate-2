package entity.film_category;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FilmCategoryId implements Serializable {
    @Column(name = "film_id", nullable = false)
    private Short filmId;

    @Column(name = "category_id", nullable = false)
    private Short categoryId;

    public FilmCategoryId() {
    }

    public FilmCategoryId(Short filmId, Short categoryId) {
        this.filmId = filmId;
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmCategoryId that)) return false;
        return Objects.equals(filmId, that.filmId) &&
                Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, categoryId);
    }
}
