package dto;

import java.time.LocalDateTime;

public record RentalInfoDTO(
        Integer id,
        String customerFirstName,
        String customerLastName,
        String filmTitle,
        LocalDateTime returnDate,
        String staffFirstName,
        String staffLastName,
        String city,
        String country
) {}
