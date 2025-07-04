package entity;

import entity.film_category.FilmCategory;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(schema = "movie", name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    private Short id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private LocalDateTime lastUpdate;



    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FilmCategory> filmCategories = new HashSet<>();



    public Category() {
    }

    public Category(String name) {
        this.name = Objects.requireNonNull(name, "Name can not be null");
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Set<FilmCategory> getFilmCategories() {
        return filmCategories;
    }

    public void setFilmCategories(Set<FilmCategory> filmCategories) {
        this.filmCategories = filmCategories;
    }

    public void addFilmCategory(FilmCategory filmCategory) {
        Objects.requireNonNull(filmCategory, "FilmCategory can not be null");

        filmCategories.add(filmCategory);
    }
}
