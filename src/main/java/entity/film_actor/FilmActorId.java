package entity.film_actor;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class FilmActorId {
    @Column(name = "film_id", nullable = false)
    private Short filmId;

    @Column(name = "actor_id", nullable = false)
    private Short actorId;

    public FilmActorId() {}

    public FilmActorId(Short filmId, Short actorId) {
        this.filmId = filmId;
        this.actorId = actorId;
    }

    public Short getFilmId() {
        return filmId;
    }

    public void setFilmId(Short filmId) {
        this.filmId = filmId;
    }

    public Short getActorId() {
        return actorId;
    }

    public void setActorId(Short actorId) {
        this.actorId = actorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilmActorId that)) return false;
        return Objects.equals(filmId, that.filmId) &&
                Objects.equals(actorId, that.actorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, actorId);
    }
}
