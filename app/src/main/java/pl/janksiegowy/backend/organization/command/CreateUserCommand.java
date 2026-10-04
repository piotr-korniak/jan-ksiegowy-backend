package pl.janksiegowy.backend.organization.command;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateUserCommand(
    String username,
    String password,
    UUID tenantId
) {
}
