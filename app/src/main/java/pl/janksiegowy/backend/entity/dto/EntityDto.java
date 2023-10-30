package pl.janksiegowy.backend.entity.dto;

import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.entity.Country;

import java.time.LocalDate;
import java.util.UUID;

public interface EntityDto {

    static Proxy create() {
        return new Proxy();
    }

    UUID getEntityId();
    String getName();
    LocalDate getDate();
    EntityType getType();
    String getTaxNumber();
    Country getCountry();
    String getAddress();
    String getPostcode();
    String getTown();
    boolean isSupplier();
    boolean isCustomer();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements EntityDto {

        private UUID entityId;
        private String name;
        private LocalDate date;
        private EntityType type;
        private String taxNumber;
        private Country country;
        private String address;
        private String postcode;
        private String town;
        private boolean supplier;
        private boolean customer;

        @Override public UUID getEntityId() {
            return entityId;
        }
        @Override public String getName() {
            return name;
        }
        @Override public LocalDate getDate() {
            return date;
        }
        @Override public EntityType getType() {
            return type;
        }
        @Override public String getTaxNumber() {
            return taxNumber;
        }
        @Override public Country getCountry() {
            return country;
        }
        @Override public String getAddress() {
            return address;
        }
        @Override public String getPostcode() {
            return postcode;
        }
        @Override public String getTown() {
            return town;
        }
        @Override public boolean isSupplier() {
            return supplier;
        }
        @Override public boolean isCustomer() {
            return customer;
        }

    }
}
