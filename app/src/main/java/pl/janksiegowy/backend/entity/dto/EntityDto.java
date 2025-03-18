package pl.janksiegowy.backend.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.entity.Country;

import java.time.LocalDate;
import java.util.UUID;

@JsonDeserialize( as= EntityDto.Proxy.class)
@JsonPropertyOrder( { "ContactId", "Name"})
public interface EntityDto {

    static Proxy create() {
        return new Proxy();
    }

    @JsonProperty( "ContactId")
    UUID getEntityId();
    String getName();
    LocalDate getDate();
    EntityType getType();
    String getTaxNumber();
    String getAccountNumber();
    Country getCountry();
    String getAddress();
    String getPostalCode();
    String getCity();
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
        private String accountNumber;
        private Country country;
        private String address;
        private String postalCode;
        private String city;
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
        @Override public String getAccountNumber() {
            return accountNumber;
        }
        @Override public Country getCountry() {
            return country;
        }
        @Override public String getAddress() {
            return address;
        }
        @Override public String getPostalCode() {
            return postalCode;
        }
        @Override public String getCity() {
            return city;
        }
        @Override public boolean isSupplier() {
            return supplier;
        }
        @Override public boolean isCustomer() {
            return customer;
        }

    }
}
