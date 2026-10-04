package pl.janksiegowy.backend.organization.command;

import lombok.Builder;

@Builder
public record CreateCompanyCommand(
        String code,
        String name
) {
}
