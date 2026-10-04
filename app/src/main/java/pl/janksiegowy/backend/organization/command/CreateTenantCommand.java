package pl.janksiegowy.backend.organization.command;

import lombok.Builder;

@Builder
public record CreateTenantCommand(
        String code,
        String name
) {
}
