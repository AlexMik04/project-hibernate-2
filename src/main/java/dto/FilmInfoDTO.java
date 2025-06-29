package dto;

import entity.Language;
import entity.film.Rating;

import java.time.Year;

public record FilmInfoDTO(
        Integer id,
        String title,
        Year releaseYear,
        String description,
        Rating rating,
        Language language,
        String category,
        String firstNameActor,
        String lastNameActor
) {}

