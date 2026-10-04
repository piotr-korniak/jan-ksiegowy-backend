package pl.janksiegowy.backend.authorization.auth.dto;

import pl.janksiegowy.backend.organization.membership.MembershipRole;

import java.util.UUID;

public record UserSummaryDto(
        UUID userId,
        String username,
        String displayName,
        MembershipRole role
) {
}
