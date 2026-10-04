package pl.janksiegowy.backend.organization.command;

import lombok.Builder;
import pl.janksiegowy.backend.organization.membership.MembershipRole;

import java.util.UUID;

@Builder
public record CreateMembershipCommand(
        UUID tenantId,
        UUID userId,
        String companyCode,
        MembershipRole role
) {
}
