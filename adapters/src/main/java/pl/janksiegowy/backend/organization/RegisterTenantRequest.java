package pl.janksiegowy.backend.organization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterTenantRequest(
        @NotBlank String nip,
        @Email @NotBlank String email,
        @NotBlank String password
) {
}
