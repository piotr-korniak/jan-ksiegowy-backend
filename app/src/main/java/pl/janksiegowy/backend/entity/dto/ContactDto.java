package pl.janksiegowy.backend.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.janksiegowy.backend.entity.Country;

import java.util.UUID;

@JsonPropertyOrder( { "ContactId", "TaxNumber", "Name",
                        "Address", "Postcode", "Town", "Country",
                        "Customer","Supplier", })
public interface ContactDto {

    @JsonProperty( "ContactId")
    UUID getEntityId();
    @JsonProperty( "Name")
    String getName();
    @JsonProperty( "TaxNumber")
    String getTaxNumber();
    @JsonProperty( "Address")
    String getAddress();
    @JsonProperty( "Postcode")
    String getPostcode();
    @JsonProperty( "Town")
    String getTown();
    @JsonProperty( "Country")
    Country getCountry();
    @JsonProperty( "Customer")
    boolean isCustomer();
    @JsonProperty( "Supplier")
    boolean isSupplier();
}
