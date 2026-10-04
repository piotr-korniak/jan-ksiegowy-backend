package pl.janksiegowy.backend.organization.membership.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.organization.membership.MembershipRole;

import java.util.UUID;

public interface MembershipDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getId();
    MembershipRole getRole();
    String getCompanyCode();
    String getCompanyName();
    String getTenantCode();
    String getTenantName();
    String getUserId();


    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements MembershipDto {
        private UUID id;
        private MembershipRole role;
        private String companyCode;
        private String companyName;
        private String tenantName;
        private String tenantCode;
        private String userId;

        @Override public UUID getId() {
            return id;
        }

        @Override
        public MembershipRole getRole() {
            return role;
        }

        @Override
        public String getCompanyCode() {
            return companyCode;
        }

        @Override
        public String getCompanyName() {
            return companyName;
        }

        @Override
        public String getTenantCode() {
            return tenantCode;
        }

        @Override
        public String getTenantName() {
            return tenantName;
        }

        @Override
        public String getUserId() {
            return userId;
        }
    }
}
