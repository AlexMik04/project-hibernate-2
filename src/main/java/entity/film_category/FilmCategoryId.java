package entity.film_category;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FilmCategoryId implements Serializable {
    @Column(name = "film_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Integer filmId;

    @Column(name = "category_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Short categoryId;

    public FilmCategoryId() {
    }

    public FilmCategoryId(Integer filmId, Short categoryId) {
        this.filmId = Objects.requireNonNull(filmId, "Film_ID can not be null");
        this.categoryId = Objects.requireNonNull(categoryId, "Category_ID can not be null");
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public Short getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Short categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "FilmCategoryId{" +
                "filmId=" + filmId +
                ", categoryId=" + categoryId +
                '}';
    }
}
