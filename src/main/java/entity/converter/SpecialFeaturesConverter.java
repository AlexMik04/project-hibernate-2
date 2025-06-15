package entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class SpecialFeaturesConverter implements AttributeConverter<Set<String>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Set<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return "";
        }
        return String.join(SEPARATOR, strings);
    }


    @Override
    public Set<String> convertToEntityAttribute(String s) {
        if (s == null || s.isBlank()) {
            return Set.of();
        }
        return Arrays.stream(s.split(SEPARATOR))
                .map(String::trim)
                .collect(Collectors.toSet());
    }
}
