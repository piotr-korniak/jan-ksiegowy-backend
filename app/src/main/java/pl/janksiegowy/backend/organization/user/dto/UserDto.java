package pl.janksiegowy.backend.organization.user.dto;

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

    String getFirstName();
    String getLastName();

    UUID getTenantId();
    UUID getLastMembershipId();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements UserDto {
        private UUID userId;
        private String username;
        private String password;

        private String firstName;
        private String lastName;

        private UUID tenantId;
        private UUID lastMembershipId;

        @Override public UUID getUserId() {
            return userId;
        }

        @Override public String getUsername() {
            return username;
        }

        @Override public String getPassword() {
            return password;
        }

        @Override public String getFirstName() {
            return firstName;
        }

        @Override public String getLastName() {
            return lastName;
        }

        @Override public UUID getTenantId() {
            return tenantId;
        }

        @Override public UUID getLastMembershipId() {
            return lastMembershipId;
        }

    }
}
