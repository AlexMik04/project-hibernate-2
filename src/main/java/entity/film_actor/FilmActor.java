package entity.film_actor;

import entity.Actor;
import entity.film.Film;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(schema = "movie", name = "film_actor")
public class FilmActor {
    @EmbeddedId
    private FilmActorId id = new FilmActorId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("filmId")
    @JoinColumn(name = "film_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("actorId")
    @JoinColumn(name = "actor_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Actor actor;

    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private LocalDateTime lastUpdate;



    public FilmActor() {
    }

    public FilmActor(Film film, Actor actor) {
        this.film = Objects.requireNonNull(film, "Film can not be null");
        this.actor = Objects.requireNonNull(actor, "Actor can not be null");
    }

    public FilmActorId getId() {
        return id;
    }

    public void setId(FilmActorId id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
