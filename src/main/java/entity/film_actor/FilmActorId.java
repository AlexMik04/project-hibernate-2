package entity.film_actor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class FilmActorId {
    @Column(name = "film_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Integer filmId;

    @Column(name = "actor_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private Integer actorId;

    public FilmActorId() {}

    public FilmActorId(Integer filmId, Integer actorId) {
        this.filmId = Objects.requireNonNull(filmId, "Film_ID can not be null");
        this.actorId = Objects.requireNonNull(actorId, "Actor_ID can not be null");
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public Integer getActorId() {
        return actorId;
    }

    public void setActorId(Integer actorId) {
        this.actorId = actorId;
    }

    @Override
    public String toString() {
        return "FilmActorId{" +
                "filmId=" + filmId +
                ", actorId=" + actorId +
                '}';
    }
}
