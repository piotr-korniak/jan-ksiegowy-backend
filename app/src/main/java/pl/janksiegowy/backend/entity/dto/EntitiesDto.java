package pl.janksiegowy.backend.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

public interface EntitiesDto {

    static Proxy create() {
        return new Proxy();
    }

    @JsonProperty( "Contacts")
    List<ContactDto> getContacts();

    @Setter
    @Accessors( fluent= true, chain= true)
    class Proxy implements EntitiesDto {

        private List<ContactDto> contacts;

        @Override public List<ContactDto> getContacts() {
            return contacts;
        }
    }
}
