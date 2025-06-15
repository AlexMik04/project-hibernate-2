package entity;

import entity.film.Film;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(schema = "movie", name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id", nullable = false)
    private Short id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private LocalDateTime lastUpdate;



    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Film> languageFilms = new HashSet<>();

    @OneToMany(mappedBy = "originalLanguage", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Film> originalLanguageFilms = new HashSet<>();



    public Language() {
    }

    public Language(String name) {
        this.name = name;
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

    public Set<Film> getLanguageFilms() {
        return languageFilms;
    }

    public void setLanguageFilms(Set<Film> languageFilms) {
        this.languageFilms = languageFilms;
    }

    public void addLanguageFilm(Film film) {
        languageFilms.add(film);
        film.setLanguage(this);
    }

    public Set<Film> getOriginalLanguageFilms() {
        return originalLanguageFilms;
    }

    public void setOriginalLanguageFilms(Set<Film> originalLanguageFilms) {
        this.originalLanguageFilms = originalLanguageFilms;
    }

    public void addOriginalLanguageFilm(Film film) {
        originalLanguageFilms.add(film);
        film.setOriginalLanguage(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language language)) return false;
        return id != null && id.equals(language.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
