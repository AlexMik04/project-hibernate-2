package entity.converter;

import entity.film.Rating;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static entity.film.Rating.NC_17;
import static entity.film.Rating.PG_13;

@Converter
public class RatingConverter implements AttributeConverter<Rating, String> {

    @Override
    public String convertToDatabaseColumn(Rating attribute) {
        if (attribute == null) {
            return null;
        }

        return switch (attribute) {
            case PG_13 -> "PG-13";
            case NC_17 -> "NC-17";
            default -> attribute.name();
        };
    }

    @Override
    public Rating convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }

        return switch (dbData) {
            case "PG-13" -> PG_13;
            case "NC-17" -> NC_17;
            default -> {
                try {
                    yield Rating.valueOf(dbData);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Unknown rating: " + dbData);
                }
            }
        };
    }
}