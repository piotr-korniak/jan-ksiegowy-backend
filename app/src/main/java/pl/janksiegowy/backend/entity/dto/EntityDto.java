package pl.janksiegowy.backend.entity.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.EntityRole;
import pl.janksiegowy.backend.entity.EntityType;
import pl.janksiegowy.backend.entity.Country;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Function;

@JsonDeserialize( as= EntityDto.Proxy.class)
@JsonIgnoreProperties( ignoreUnknown= true)
public interface EntityDto {

    static Proxy create( String taxNumber) {
        return new Proxy();
    }

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

        @JsonAlias( "Name")
        private String name;

        @JsonAlias( "Date")
        private LocalDate date;

        @JsonAlias( "Type")
        @NotNull( message= "Type is require")
        private EntityType type;

        @JsonAlias( "Tax Number")
        private String taxNumber;

        private String accountNumber;

        @JsonAlias( "Country")
        private Country country= Country.PL;

        @JsonAlias( "Address")
        private String address;

        @JsonAlias( "PostalCode")
        private String postalCode;

        @JsonAlias( "City")
        private String city;

        @JsonSetter( "Role")
        private EntityRole role;

        public Proxy setTaxNumber( String taxNumber) {
            if( taxNumber.isEmpty())
                return this;

            this.taxNumber= taxNumber.replaceAll("[^a-zA-Z0-9]", "");

            if( !this.taxNumber.matches("\\d+")) {
                this.country= Country.valueOf( this.taxNumber.substring( 0, 2));
                this.taxNumber= this.taxNumber.substring(2);
            }
            return this;
        }

        public Proxy taxNumber( String taxNumber) {
            return setTaxNumber( taxNumber);
        }

        public EntityDto applyIf( boolean condition, Function<Proxy, Proxy> function) {
            return condition ? function.apply(this) : this;
        }

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
            return role!= null && role.isSupplier();
        }
        @Override public boolean isCustomer() {
            return role!= null && role.isCustomer();
        }

    }
}
