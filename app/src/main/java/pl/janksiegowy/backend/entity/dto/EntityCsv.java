package pl.janksiegowy.backend.entity.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.janksiegowy.backend.entity.Country;
import pl.janksiegowy.backend.entity.EntityRole;
import pl.janksiegowy.backend.entity.EntityType;

import java.time.LocalDate;

@JsonDeserialize( as= EntityCsv.Proxy.class)
public interface EntityCsv {

    public EntityType getType();
    public LocalDate getDate();
    public String getName();
    public String getTaxNumber();
    public String getAddress();
    public String getPostalCode();
    public Country getCountry();
    public String getCity();
    public EntityRole getRole();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements EntityCsv {

        @JsonCreator
        public Proxy( @JsonProperty( "Tax Number") String taxNumber) {
            this.taxNumber= taxNumber.replaceAll("[^a-zA-Z0-9]", "");

            if (!this.taxNumber.matches("\\d+")) {
                this.country= Country.valueOf( this.taxNumber.substring( 0, 2));
                this.taxNumber= this.taxNumber.substring(2);
            }
        }

        @JsonProperty( "Type")
        EntityType type;

        @JsonProperty( "Date")
        private LocalDate date;

        @JsonProperty( "Name")
        private String name;

        @JsonProperty( "Tax Number")
        private String taxNumber;

        @JsonProperty( "Address")
        private String address;

        @JsonProperty( "PostalCode")
        private String postalCode;

        @JsonProperty( "City")
        private String city;

        @JsonProperty( "Role")
        private EntityRole role;

        @JsonProperty( "Country")
        private Country country= Country.PL;

        @Override public EntityType getType() {
            return type;
        }

        @Override public LocalDate getDate() {
            return date;
        }

        @Override public String getName() {
            return name;
        }

        @Override public String getTaxNumber() {
            return taxNumber;
        }

        @Override public String getAddress() {
            return address;
        }

        @Override public String getPostalCode() {
            return postalCode;
        }

        @Override public Country getCountry() {
            return country;
        }

        @Override public String getCity() {
            return city;
        }

        @Override public EntityRole getRole() {
            return role;
        }
    }
}
