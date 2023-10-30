package pl.janksiegowy.backend.entity;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import pl.janksiegowy.backend.entity.dto.EntityDto;
import pl.janksiegowy.backend.shared.DataLoader;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class EntityInitializer {

    private final DataLoader loader;
    private final EntityQueryRepository entities;
    private final EntityFacade facade;
    private final DateTimeFormatter formatter= DateTimeFormatter.ofPattern( "--- dd.MM.yyyy");

    public void init() {
        LocalDate histDate= LocalDate.now();

        for( String line: loader.readEntities()) {
            String[] fields= loader.getFields( line);

            if( fields[0].startsWith( "--- ")) {    // set date
                histDate= LocalDate.parse( fields[0], formatter);
                continue;
            }

            var entity= create( fields).date( histDate);

            entities.findByCountryAndTypeAndTaxNumber(
                        entity.getCountry(), entity.getType(), entity.getTaxNumber())
                .ifPresentOrElse( entityDto-> {
                    if( entityDto.getDate().isBefore( entity.getDate()))
                        facade.save( entity.entityId( entityDto.getEntityId()));},
                ()-> facade.save( entity));
        }
    }

    private EntityDto.Proxy create( String[] fields) {
        var role= EntityRole.values()[Integer.parseInt( fields[6])];
        return EntityDto.create()
                .type( EntityType.valueOf( fields[0]))
                .name( fields[1])
                .taxNumber( fields[2].replaceAll( "[^a-zA-Z0-9]", ""))
                .address( fields[3])
                .postcode( fields[4])
                .town( fields[5])
                .supplier( role.isSupplier())
                .customer( role.isCustomer())                       // PL default
                .country( fields.length>7? Country.valueOf(fields[7]): Country.PL);
    }

    private enum EntityRole {   // decode entities Role
        NONE,
        SUPPLIER {
            @Override public boolean isSupplier() {
                return true;
            }
        },
        CUSTOMER {
            @Override public boolean isCustomer() {
                return true;
            }
        },
        BOTH {
            @Override public boolean isSupplier() {
                return true;
            }

            @Override public boolean isCustomer() {
                return true;
            }
        };

        public boolean isSupplier() {
            return false;
        };
        public boolean isCustomer() {
            return false;
        };
    }
}
