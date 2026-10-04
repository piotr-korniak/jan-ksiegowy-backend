package pl.janksiegowy.backend.authorization.auth.dto;

import java.util.List;

public record LoginResponseDto(
        boolean authenticated,
        boolean requiresCompanySelection,
        String accessToken,
        String refreshToken,
        UserSummaryDto user,
        List<CompanyOptionDto> companies
) {
}
