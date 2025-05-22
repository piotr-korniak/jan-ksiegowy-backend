package pl.janksiegowy.backend.user.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

public interface UserDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getUserId();
    String getUsername();
    String getPassword();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements UserDto {
        private UUID userId;
        private String username;
        private String password;

        @Override public UUID getUserId() {
            return userId;
        }

        @Override public String getUsername() {
            return username;
        }

        @Override public String getPassword() {
            return password;
        }
    }
}
