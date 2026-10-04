package pl.janksiegowy.backend.authorization.auth.dto;

import java.util.UUID;

public record CompanyOptionDto(
        UUID membershipId,
        String companyCode,
        String companyName
) {
}
