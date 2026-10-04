package pl.janksiegowy.backend.authorization.auth.dto;

public record LoginRequestDto(
        String email,
        String password
) {}
