package pl.janksiegowy.backend.organization.command;

public record RegisterTenantWithInitialCompanyCommand(
        String nip,
        String email,
        String password,
        String code
) {
}
