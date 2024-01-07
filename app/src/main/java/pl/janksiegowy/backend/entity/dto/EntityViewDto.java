package pl.janksiegowy.backend.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.UUID;


@JsonPropertyOrder({ "ContactId", "Name"})
public interface EntityViewDto {

    @JsonProperty( "ContactId")
    UUID getEntityId();
    @JsonProperty( "Name")
    String getName();



}
