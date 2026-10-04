package pl.janksiegowy.backend.authorization.auth.dto;

import java.util.UUID;

public record SelectCompanyRequestDto(
        UUID membershipId
) {
}
