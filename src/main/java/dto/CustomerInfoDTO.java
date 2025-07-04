package dto;

public record CustomerInfoDTO(
        Integer id,
        String firstName,
        String lastName,
        String email,
        Boolean active,
        String city,
        String country
) {}

